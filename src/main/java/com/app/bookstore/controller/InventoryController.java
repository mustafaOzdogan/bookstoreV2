package com.app.bookstore.controller;

import com.app.bookstore.dto.BookStock;
import com.app.bookstore.model.Book;
import com.app.bookstore.model.Bookstore;
import com.app.bookstore.model.CommonResponse;
import com.app.bookstore.model.Inventory;
import com.app.bookstore.service.BookService;
import com.app.bookstore.service.BookstoreService;
import com.app.bookstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController
{
    @Autowired
    private BookstoreService bookstoreService;

    @Autowired
    private BookService bookService;

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping(path="bookstores/{bookstoreId}/books", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addBookToBookstore(@PathVariable("bookstoreId") String bookstoreId, @RequestBody BookStock bookStock)
    {
        CommonResponse response;
        try
        {
            // TODO : REFACTOR
            Bookstore bookstore = bookstoreService.getBookstoreById(Long.valueOf(bookstoreId));
            if(bookstore == null)
                throw new Exception("Bookstore Is Not Found for Id:" + bookstoreId);

            Book book = bookService.getBookById(bookStock.getBookId());
            if(book == null)
                throw new Exception("Book Is Not Found for Id:" + bookStock.getBookId());

            Inventory createdInventory = Inventory.builder()
                    .bookId(book.getId())
                    .bookstoreId(bookstore.getId())
                    .price(bookStock.getPrice())
                    .build();

            inventoryService.addInventory(createdInventory);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(createdInventory)
                    .message("The book is successfully added to the bookstore.")
                    .code(HttpStatus.CREATED.value())
                    .build();
        }
        catch (Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("The book cannot be added to the bookstore.")
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="bookstores/{bookstoreId}/books/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<CommonResponse> removeBookInBookstore(@PathVariable("bookstoreId") String bookstoreId, @PathVariable("bookId") String bookId)
    {
        CommonResponse response;
        try
        {
            // TODO REFACTOR
            Bookstore bookstore = bookstoreService.getBookstoreById(Long.valueOf(bookstoreId));
            if(bookstore == null)
                throw new Exception("Bookstore Is Not Found for Id:" + bookstoreId);

            // TODO REFACTOR
            Inventory inventory = inventoryService.getBookSellingInBookstore(Long.valueOf(bookId), Long.valueOf(bookstoreId));
            if(inventory == null)
                throw new Exception("Inventory Is Not Found for Book Id:" + bookId + " with Bookstore Id:" + bookstoreId);

            inventoryService.removeInventory(inventory.getId());

            response = new CommonResponse.Builder()
                    .success(true)
                    .message("The book is successfully removed from the bookstore.")
                    .code(HttpStatus.MOVED_PERMANENTLY.value())
                    .build();
        }
        catch (Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .message("The book cannot be removed from the bookstore.")
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .internalMessage(e.getMessage())
                    .build();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}