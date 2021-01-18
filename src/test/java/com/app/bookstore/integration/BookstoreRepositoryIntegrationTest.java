package com.app.bookstore.integration;

import com.app.bookstore.model.Bookstore;
import com.app.bookstore.repository.BookstoreRepository;
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
public class BookstoreRepositoryIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookstoreRepository bookstoreRepository;

    @Test
    public void whenFindById_thenReturnBookstore()
    {
        Bookstore bookstore = Bookstore.builder()
                                       .name("nezih")
                                       .city("istanbul")
                                       .build();

        entityManager.persistAndFlush(bookstore);

        Bookstore found = bookstoreRepository.findById(bookstore.getId()).orElse(null);

        assertThat(found.getName()).isEqualTo(bookstore.getName());
        assertThat(found.getCity()).isEqualTo(bookstore.getCity());
    }

    @Test
    public void whenInvalidId_thenReturnNull()
    {
        long invalidId = -99;
        Bookstore found = bookstoreRepository.findById(invalidId).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    public void givenSetOfBookstores_whenFindAll_thenReturnAllBookstores()
    {
        Bookstore nezihIst     = Bookstore.builder().name("nezihIst").city("istanbul").build();
        Bookstore nezihNigde   = Bookstore.builder().name("nezihNigde").city("niğde").build();
        Bookstore ugurYayınevı = Bookstore.builder().name("ugurYayınevi").city("istanbul").build();

        entityManager.persist(nezihIst);
        entityManager.persist(nezihNigde);
        entityManager.persist(ugurYayınevı);
        entityManager.flush();

        List<Bookstore> allCategories = bookstoreRepository.findAll();

        // TODO ALL FIELDS
        assertThat(allCategories).hasSize(3).extracting(Bookstore::getName)
                .contains(nezihIst.getName(), nezihNigde.getName(), ugurYayınevı.getName());
    }
}
