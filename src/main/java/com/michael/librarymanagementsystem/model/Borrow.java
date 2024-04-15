package com.michael.librarymanagementsystem.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", referencedColumnName = "id", nullable = false)
    private Patron patron;

    @Column(name = "borrowing_date")
    @NotNull
    private Timestamp borrowingDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @Column(name = "is_returned")
    private boolean isReturned;

    public Borrow(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.borrowingDate = new Timestamp(System.currentTimeMillis());
    }

    public void doReturn() {
        setReturned(true);
        setReturnDate(new Timestamp(System.currentTimeMillis()));
        book.returnBook();
    }
}
