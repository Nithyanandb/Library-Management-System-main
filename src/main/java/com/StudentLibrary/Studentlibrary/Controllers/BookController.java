package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/createBook")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>("Book added to the library system", HttpStatus.CREATED);
    }

    @GetMapping("/getBooks")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "genre", required = false) String genre,
                                               @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                               @RequestParam(value = "author", required = false) String author) {
        List<Book> bookList = bookService.getBooks(genre, available, author);
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted) {
            return new ResponseEntity<>("Book successfully deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}
