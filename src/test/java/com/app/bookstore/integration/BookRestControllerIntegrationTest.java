package com.app.bookstore.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.bookstore.BookstoreV2Application;
import com.app.bookstore.model.Book;
import com.app.bookstore.model.Category;
import com.app.bookstore.service.BookService;
import com.app.bookstore.service.CategoryService;
import com.app.bookstore.util.JsonUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BookstoreV2Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")

public class BookRestControllerIntegrationTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @After
    public void resetDb()
    {
        bookService.removeAllBooks();
    }

    @Test
    public void givenValidInput_thenCreateBook() throws Exception
    {

        Category history    = createTestCategory("history");
        Book einsteinLife   = Book.builder().name("Einstein's Life").categoryId(history.getId()).build();
        mvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(einsteinLife)));

        // TODO JUST NAME FIELD NEED ?
        List<Book> found = bookService.getAllBooks();
        assertThat(found).extracting(Book::getName).containsOnly(einsteinLife.getName());
    }

    @Test
    public void whenGetProducts_thenStatus200() throws Exception
    {
        Book einsteinLife   = createTestBook("Einstein's Life", 1);
        Book secretOfWorld  = createTestBook("The secret of world", 2);

        mvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.data[0].name", is(einsteinLife.getName())))
            .andExpect(jsonPath("$.data[0].categoryId").value(einsteinLife.getCategoryId()))
            .andExpect(jsonPath("$.data[1].name", is(secretOfWorld.getName())))
            .andExpect(jsonPath("$.data[1].categoryId").value(secretOfWorld.getCategoryId()));
    }

    private Book createTestBook(String bookName, long bookCategoryId)
    {
        Book book   = Book.builder().name(bookName).categoryId(bookCategoryId).build();
        bookService.persist(book);
        return book;
    }

    private Category createTestCategory(String categoryName)
    {
        Category category = Category.builder().name(categoryName).build();
        categoryService.persist(category);
        return category;
    }

}
