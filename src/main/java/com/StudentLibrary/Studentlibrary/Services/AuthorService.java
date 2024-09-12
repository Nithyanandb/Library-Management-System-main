// AuthorService.java
package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.Author;
import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthorsByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> getAuthorsByCountry(String country) {
        return authorRepository.findByCountry(country);
    }

    // Updates author if found
    public void updateAuthor(Author author) {
        if (authorRepository.existsById(author.getId())) {
            authorRepository.save(author); // Spring Data JPA automatically updates if ID exists
        } else {
            throw new RuntimeException("Author with ID " + author.getId() + " not found.");
        }
    }

    // Saves a new author
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    // Deletes an author by ID
    public void deleteAuthor(int id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Author with ID " + id + " not found.");
        }
    }
}
