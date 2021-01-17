package com.app.bookstore.service;

import com.app.bookstore.model.Bookstore;

import java.util.List;

public interface BookstoreService
{
    Bookstore getBookstoreById(Long id);
    Bookstore addBookstore(Bookstore bookstore);
    List<Bookstore> getAllBookstores();
}
