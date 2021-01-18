package com.app.bookstore.integration;

import com.app.bookstore.model.Book;
import com.app.bookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindById_thenReturnBook()
    {
        Book book = Book.builder().name("Einstein's Life").categoryId(1).build();

        entityManager.persistAndFlush(book);

        Book found = bookRepository.findById(book.getId()).orElse(null);

        assertThat(found.getName()).isEqualTo(book.getName());
        assertThat(found.getCategoryId()).isEqualTo(book.getCategoryId());
    }

    @Test
    public void whenInvalidId_thenReturnNull()
    {
        long invalidId = -99;
        Book found = bookRepository.findById(invalidId).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    public void givenSetOfBooks_whenFindAll_thenReturnAllBooks()
    {
        Book einsteinLife   = Book.builder().name("Einstein's Life").categoryId(1).build();
        Book secretOfWorld  = Book.builder().name("The secret of world").categoryId(2).build();
        Book heisenbergOne  = Book.builder().name("Heisenberg's Life I").categoryId(1).build();

        entityManager.persist(einsteinLife);
        entityManager.persist(secretOfWorld);
        entityManager.persist(heisenbergOne);
        entityManager.flush();

        List<Book> allCategories = bookRepository.findAll();

        // TODO ALL FIELDS
        assertThat(allCategories).hasSize(3).extracting(Book::getName)
                .contains(einsteinLife.getName(), secretOfWorld.getName(), heisenbergOne.getName());
    }
}