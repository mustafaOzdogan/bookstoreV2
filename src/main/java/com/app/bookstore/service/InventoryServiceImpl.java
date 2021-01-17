package com.app.bookstore.service;

import com.app.bookstore.model.Inventory;
import com.app.bookstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryServiceImpl implements InventoryService
{
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Long> getBookIdsSellingByBookstore(Long bookstoreId) { return inventoryRepository.findBookIdsSellingByBookstore(bookstoreId); }

    @Override
    public Inventory getBookSellingInBookstore(Long bookId, Long bookstoreId) { return inventoryRepository.findBookSellingByBookstore(bookId, bookstoreId); }

    @Override
    public Inventory addInventory(Inventory inventory) { return inventoryRepository.save(inventory); }

    @Override
    public void removeInventory(Long inventoryId) { inventoryRepository.deleteById(inventoryId); }
}