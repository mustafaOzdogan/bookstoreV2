package com.app.bookstore.integration;

import com.app.bookstore.model.Inventory;
import com.app.bookstore.repository.InventoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InventoryRepositoryIntegrationTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void whenFindById_thenReturnInventory()
    {
        Inventory inventory = Inventory.builder().bookId(1).bookstoreId(90).price(BigDecimal.valueOf(100)).build();

        entityManager.persistAndFlush(inventory);

        Inventory found = inventoryRepository.findById(inventory.getId()).orElse(null);

        assertThat(found.getBookId()).isEqualTo(inventory.getBookId());
        assertThat(found.getBookstoreId()).isEqualTo(inventory.getBookstoreId());
        assertThat(found.getPrice()).isEqualTo(inventory.getPrice());
    }

    @Test
    public void whenInvalidId_thenReturnNull()
    {
        long invalidId = -99;
        Inventory found = inventoryRepository.findById(invalidId).orElse(null);
        assertThat(found).isNull();
    }

    @Test
    public void givenSetOfInventorys_whenFindAll_thenReturnAllInventorys()
    {
        Inventory inventory_1 = Inventory.builder().bookId(1).bookstoreId(90).price(BigDecimal.valueOf(100)).build();
        Inventory inventory_2 = Inventory.builder().bookId(13).bookstoreId(92).price(BigDecimal.valueOf(1500)).build();
        Inventory inventory_3 = Inventory.builder().bookId(11).bookstoreId(90).price(BigDecimal.valueOf(1200)).build();

        entityManager.persist(inventory_1);
        entityManager.persist(inventory_2);
        entityManager.persist(inventory_3);
        entityManager.flush();

        List<Inventory> allCategories = inventoryRepository.findAll();

        // TODO ALL FIELDS
        assertThat(allCategories).hasSize(3).extracting(Inventory::getPrice)
                .contains(inventory_1.getPrice(), inventory_2.getPrice(), inventory_3.getPrice());
    }
}
