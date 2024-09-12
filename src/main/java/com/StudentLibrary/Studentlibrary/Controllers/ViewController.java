package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Model.Student;
import com.StudentLibrary.Studentlibrary.Services.AuthorService;
import com.StudentLibrary.Studentlibrary.Services.BookService;
import com.StudentLibrary.Studentlibrary.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class ViewController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("author", new Author()); // For form binding
        return "authors";
    }

    @PostMapping("/addauthors")
    public String addAuthor(@ModelAttribute Author author, Model model) {
        authorService.saveAuthor(author);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("book", new Book()); // For form binding
        return "books";
    }

    @PostMapping("/addbooks")
    public String addBook(@ModelAttribute Book book, Model model) {
        bookService.saveBook(book);
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }


    @GetMapping("/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student()); // For form binding
        return "students";
    }


    @PostMapping("/addstudents")
    public String addStudent(@ModelAttribute Student student, Model model) {
        studentService.saveStudent(student);
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/transactions")
    public String getTransactions(Model model) {
        // Add logic to populate transactions if needed
        return "transactions";
    }

    @GetMapping("/cards")
    public String showCards(Model model) {
        // Add logic to show cards if needed
        return "cards";
    }

    @GetMapping("/editstudent/{id}")
    public String editStudent(@PathVariable int id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "editstudent"; // Name of the Thymeleaf template for editing
    }

    @PostMapping("/updatestudent")
    public String updateStudent(@ModelAttribute Student student, Model model) {
        studentService.updateStudent(student.getId(), student);
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }


    @GetMapping("/deletestudent/{id}")
    public String deleteStudent(@PathVariable int id, Model model) {
        studentService.deleteStudentById(id);
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
}

