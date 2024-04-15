package com.michael.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.michael.librarymanagementsystem.model.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long>  {

}
