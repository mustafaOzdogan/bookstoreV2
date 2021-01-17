package com.app.bookstore.controller;

import com.app.bookstore.model.Bookstore;
import com.app.bookstore.model.CommonResponse;
import com.app.bookstore.service.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookstoreController
{
    @Autowired
    private BookstoreService bookstoreService;

    @RequestMapping(path="bookstores", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addBookstore(@RequestBody Bookstore bookstore)
    {
        CommonResponse response;
        try
        {
            Bookstore createdBookstore = bookstoreService.addBookstore(bookstore);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(createdBookstore)
                    .code(HttpStatus.CREATED.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Product cannot be added.")
                    .code(HttpStatus.EXPECTATION_FAILED.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(path="bookstores", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getBookstores()
    {
        CommonResponse response;
        try
        {
            List<Bookstore> bookstores = bookstoreService.getAllBookstores();

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(bookstores)
                    .code(HttpStatus.FOUND.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Bookstores cannot be retrieved.")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
