package com.michael.librarymanagementsystem.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.michael.librarymanagementsystem.exception.PatronNotFoundException;
import com.michael.librarymanagementsystem.model.Patron;
import com.michael.librarymanagementsystem.repository.PatronRepository;

@Service
@Transactional
public class PatronService {

    @Autowired
    PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Patron updatePatron(Long id, Patron newPatron) {

        Patron patron = getPatronById(id);
        patron.setName(Objects.toString(newPatron.getName(), patron.getName()));
        patron.setAddress(Objects.toString(newPatron.getAddress(), patron.getAddress()));
        patron.setPhoneNumber(Objects.toString(newPatron.getPhoneNumber(), patron.getPhoneNumber()));
        patron.setEmailAddress(Objects.toString(newPatron.getEmailAddress(), patron.getEmailAddress()));

        return patronRepository.save(patron);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

}
