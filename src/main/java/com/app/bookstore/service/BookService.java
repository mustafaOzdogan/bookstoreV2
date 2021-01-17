package com.app.bookstore.service;

import com.app.bookstore.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService
{
    Book getBookById(Long id);
    Book addBook(Book book);
    Book updateBook(Book book) throws Exception;
    List<Book> getAllBooks();
    List<Book> getBooksByCategoryId(Long categoryId);
    List<Book> getBooksByBookstoreId(Long bookstoreId) throws Exception;
}
