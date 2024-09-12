package com.StudentLibrary.Studentlibrary.Repositories;

import com.StudentLibrary.Studentlibrary.Model.Transaction;
import com.StudentLibrary.Studentlibrary.Model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
      Optional<Transaction> findById(String transactionId);
    List<Transaction> findByCardIdAndBookIdAndTransactionStatusAndIsIssueOperation(int cardId, int bookId, TransactionStatus status, boolean isIssueOperation);


}
