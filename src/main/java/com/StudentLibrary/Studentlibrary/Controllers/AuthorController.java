package com.StudentLibrary.Studentlibrary.Controllers;

import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable int id, @RequestBody Author author) {
        author.setId(id);  // Ensure the ID is set in the Author object
        authorService.updateAuthor(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping("addAuthors")
    public void addAuthorIn(@PathVariable Author author)
    {
        authorService.saveAuthor(author);
    }


}
