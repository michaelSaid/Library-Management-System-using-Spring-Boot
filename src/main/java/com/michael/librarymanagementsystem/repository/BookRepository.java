package com.michael.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michael.librarymanagementsystem.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
