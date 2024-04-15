package com.michael.librarymanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.librarymanagementsystem.model.Borrow;
import com.michael.librarymanagementsystem.service.BorrowingService;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    @Autowired
    BorrowingService borrowingService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public Borrow borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public void returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
    }

}
