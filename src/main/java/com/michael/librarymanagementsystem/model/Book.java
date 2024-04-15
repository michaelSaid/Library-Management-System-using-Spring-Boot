package com.michael.librarymanagementsystem.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    @NotNull(message = "Book title must not be Null")
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "author", nullable = false)
    @NotNull(message = "Book author must not be Null")
    private String author;

    @Column(name = "isbn", length = 50, nullable = false, unique = true)
    @NotNull(message = "Book ISBN must not be Null")
    private String isbn;

    @Column(name = "publication_year")
    @NotNull(message = "Book publication year must not be Null")
    private Timestamp publicationYear;

    private Integer quantity = 0;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Borrow> borrows;

    public boolean canBorrow() {
        return quantity > 0;
    }

    public void borrow() {
        if (canBorrow()) {
            quantity--;
        }
    }

    public void returnBook() {
        quantity++;
    }
}
