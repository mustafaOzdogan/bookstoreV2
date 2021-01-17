package com.app.bookstore.controller;

import com.app.bookstore.model.Book;
import com.app.bookstore.model.Category;
import com.app.bookstore.model.CommonResponse;
import com.app.bookstore.service.BookService;
import com.app.bookstore.service.BookstoreService;
import com.app.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController
{
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookstoreService bookstoreService;

    @RequestMapping(path="books", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getBooks()
    {
        CommonResponse response;
        try
        {
            List<Book> books = bookService.getAllBooks();

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(books)
                    .code(HttpStatus.FOUND.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Books cannot be retrieved.")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="books", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addBook(@RequestBody Book book)
    {
        CommonResponse response;
        try
        {
            // TODO : refactor needs
            Category category = categoryService.getCategoryById(book.getCategoryId());
            if(category == null)
                throw new Exception("Category Not Found for Id:" + book.getCategoryId());

            Book createdBook = bookService.addBook(book);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(createdBook)
                    .code(HttpStatus.CREATED.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Book cannot be added.")
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="categories/{categoryId}/books", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getBooksByCategoryId(@PathVariable("categoryId") String categoryId)
    {
        CommonResponse response;
        try
        {
            // TODO: REFACTOR  NULL - CHECK
            Category category = categoryService.getCategoryById(Long.valueOf(categoryId));
            if(category == null)
                throw new Exception("Category Not Found for Id:" + categoryId);

            // TODO: REFACTOR  SIZE - CHECK
            List<Book> books = bookService.getBooksByCategoryId(Long.valueOf(categoryId));
            if(books.size() == 0)
                throw new Exception("Book Not Found for Category Id:" + categoryId);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(books)
                    .code(HttpStatus.FOUND.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Books cannot be retrieved.")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="bookstores/{bookstoreId}/books", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getBooksByBookstoreId(@PathVariable("bookstoreId") String bookstoreId)
    {
        CommonResponse response;
        try
        {
            // TODO: REFACTOR  SIZE - CHECK
            List<Book> books = bookService.getBooksByBookstoreId(Long.valueOf(bookstoreId));
            if(books.size() == 0)
                throw new Exception("Book Not Found for Bookstore Id:" + bookstoreId);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(books)
                    .code(HttpStatus.FOUND.value())
                    .build();

        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Books cannot be retrieved.")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="books", method = RequestMethod.PUT)
    public ResponseEntity<CommonResponse> updateBook(@RequestBody Book book)
    {
        CommonResponse response;
        try
        {
            Book updatedBook = bookService.updateBook(book);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(updatedBook)
                    .code(HttpStatus.OK.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Book Cannot be Updated for Id:" + book.getId())
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
