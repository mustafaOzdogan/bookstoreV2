package com.app.bookstore.service;

import com.app.bookstore.model.Bookstore;
import com.app.bookstore.repository.BookstoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookstoreServiceImpl implements BookstoreService {
    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Override
    public Bookstore getBookstoreById(Long id) {
        return bookstoreRepository.findById(id).orElse(null);
    }

    @Override
    public Bookstore addBookstore(Bookstore bookstore) {
        return bookstoreRepository.save(bookstore);
    }

    @Override
    public List<Bookstore> getAllBookstores() {
        return bookstoreRepository.findAll();
    }
}