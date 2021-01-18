package com.app.bookstore.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.bookstore.model.Category;
import com.app.bookstore.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindById_thenReturnCategory()
    {
        Category history = Category.builder()
                                    .name("history")
                                    .build();

        entityManager.persistAndFlush(history);

        Category found = categoryRepository.findById(history.getId()).orElse(null);

        assertThat(found.getName()).isEqualTo(history.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull()
    {
        long invalidId = -99;
        Category found = categoryRepository.findById(invalidId).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    public void givenSetOfCategories_whenFindAll_thenReturnAllCategories()
    {
        Category history        = Category.builder().name("history").build();
        Category literature     = Category.builder().name("literature").build();
        Category scienceFiction = Category.builder().name("scienceFiction").build();

        entityManager.persist(history);
        entityManager.persist(literature);
        entityManager.persist(scienceFiction);
        entityManager.flush();

        List<Category> allCategories = categoryRepository.findAll();

        assertThat(allCategories).hasSize(3).extracting(Category::getName)
                                 .contains(history.getName(), literature.getName(), scienceFiction.getName());
    }
}
