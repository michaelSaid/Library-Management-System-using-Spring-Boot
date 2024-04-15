package com.michael.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.michael.librarymanagementsystem.model.Book;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class BookOutOfStockException extends RuntimeException {
    public BookOutOfStockException(Book book) {
        super("The book " + book.getTitle() + " is out of stock!");
    }
}
