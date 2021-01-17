package com.app.bookstore.repository;

import com.app.bookstore.model.Book;

import java.util.List;

public interface BookRepositoryCustom
{
    List<Book> findBooksByCategoryId(Long categoryId);
    List<Book> findBooksByBookstoreId(Long bookstoreId) throws Exception;
}
