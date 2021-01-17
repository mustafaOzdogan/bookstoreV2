package com.app.bookstore.controller;

import com.app.bookstore.model.Category;
import com.app.bookstore.model.CommonResponse;
import com.app.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(path="categories", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse> addCategory(@RequestBody Category category)
    {
        CommonResponse response;
        try
        {
            Category createdCategory = categoryService.addCategory(category);

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(createdCategory)
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

    @RequestMapping(path="categories", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse> getCategories()
    {
        CommonResponse response;
        try
        {
            List<Category> categories = categoryService.getAllCategories();

            response = new CommonResponse.Builder()
                    .success(true)
                    .data(categories)
                    .code(HttpStatus.FOUND.value())
                    .build();
        }
        catch(Exception e)
        {
            response = new CommonResponse.Builder()
                    .success(false)
                    .internalMessage(e.getMessage())
                    .message("Categories cannot be retrieved.")
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
