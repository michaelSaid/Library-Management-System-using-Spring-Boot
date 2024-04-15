package com.michael.librarymanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.michael.librarymanagementsystem.model.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("select b from Borrow b where b.book.id = :bookId and b.patron.id = :patronId and b.isReturned = false")
    List<Borrow> findIfAlreadyBorrowed(@Param("bookId") Long bookId, @Param("patronId") Long patronId);

}
