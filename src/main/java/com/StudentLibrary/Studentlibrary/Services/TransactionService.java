package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.*;
import com.StudentLibrary.Studentlibrary.Repositories.BookRepository;
import com.StudentLibrary.Studentlibrary.Repositories.CardRepository;
import com.StudentLibrary.Studentlibrary.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;

    @Value("${books.max_allowed}")
    private int maxAllowedBooks;

    @Value("${books.max_allowed_days}")
    private int maxDaysAllowed;

    @Value("${books.fine.per_day}")
    private int finePerDay;

    @Transactional
    public String issueBooks(int cardId, int bookId) throws Exception {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Book not found!"));
        if (!book.isAvailable()) {
            throw new Exception("Book is not available!");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new Exception("Card not found!"));
        if (card.getCardStatus() == CardStatus.DEACTIVATED) {
            throw new Exception("Card is deactivated!");
        }

        if (card.getBooks().size() >= maxAllowedBooks) {
            throw new Exception("Book limit reached!");
        }

        book.setAvailable(false);
        book.setCard(card);
        card.getBooks().add(book);
        cardRepository.save(card);
        bookRepository.save(book);

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setBook(book);
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transaction.setTransactionDate(new Date());
        transactionRepository.save(transaction);

        logger.info("Issued book with ID {} to card with ID {}", bookId, cardId);
        return transaction.getTransactionId();
    }

    @Transactional
    public String returnBooks(int cardId, int bookId) throws Exception {

        List<Transaction> transactions = transactionRepository.findByCardIdAndBookIdAndTransactionStatusAndIsIssueOperation(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        if (transactions.isEmpty()) {
            throw new Exception("No transaction found for the provided book and card!");
        }

        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        Date issueDate = lastTransaction.getTransactionDate();
        long numberOfDaysPassed = ChronoUnit.DAYS.between(issueDate.toInstant(), Instant.now());
        int fine = 0;

        if (numberOfDaysPassed > maxDaysAllowed) {
            fine = (int) ((numberOfDaysPassed - maxDaysAllowed) * finePerDay);
        }

        Card card = lastTransaction.getCard();
        Book book = lastTransaction.getBook();
        book.setCard(null);
        book.setAvailable(true);
        bookRepository.save(book);

        Transaction newTransaction = new Transaction();
        newTransaction.setBook(book);
        newTransaction.setCard(card);
        newTransaction.setFineAmount(fine);
        newTransaction.setIssueOperation(false);
        newTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        newTransaction.setTransactionDate(new Date());
        transactionRepository.save(newTransaction);

        logger.info("Returned book with ID {} from card with ID {}. Fine amount: {}", bookId, cardId, fine);
        return newTransaction.getTransactionId();
    }

    public Transaction getTransactionById(String transactionId) throws Exception {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transaction not found!"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
