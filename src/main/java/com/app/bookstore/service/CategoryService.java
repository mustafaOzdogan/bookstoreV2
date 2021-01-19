package com.app.bookstore.service;

import com.app.bookstore.model.Book;
import com.app.bookstore.model.Category;

import java.util.List;

public interface CategoryService
{
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    List<Category> getAllCategories();
    void persist(Category category);
}
