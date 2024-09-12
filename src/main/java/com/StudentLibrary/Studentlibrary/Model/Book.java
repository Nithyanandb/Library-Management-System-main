package com.StudentLibrary.Studentlibrary.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

 @Entity
 public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int id;

        private String name;

        @Enumerated(EnumType.STRING)
        private Genre genre;

        @ManyToOne
        @JoinColumn(name = "author_id")
        @JsonIgnore
        private Author author;

        @ManyToOne
        @JoinColumn(name = "card_id", referencedColumnName = "id") // Ensure this column name is correct and does not conflict
        private Card card;

        @Column(columnDefinition = "TINYINT(1)")
        private boolean available;

        @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonIgnore
        private List<Transaction> transactions;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
