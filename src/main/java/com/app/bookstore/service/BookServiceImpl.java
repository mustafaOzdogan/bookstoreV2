package com.app.bookstore.service;

import com.app.bookstore.model.Book;
import com.app.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookServiceImpl implements BookService
{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) throws Exception
    {
        Book dbBook = getBookById(book.getId());

        if(dbBook == null)
            throw new Exception("Book Is Not Found for Id:" + book.getId());
        else
            return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByCategoryId(Long id) { return bookRepository.findBooksByCategoryId(id); }

    @Override
    public List<Book> getBooksByBookstoreId(Long id) throws Exception { return bookRepository.findBooksByBookstoreId(id); }
}
