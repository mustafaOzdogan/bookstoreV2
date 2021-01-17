package com.app.bookstore.service;

import com.app.bookstore.model.Inventory;

import java.util.List;

public interface InventoryService
{
    List<Long> getBookIdsSellingByBookstore(Long bookstoreId);
    Inventory getBookSellingInBookstore(Long bookId, Long bookstoreId);
    Inventory addInventory(Inventory inventory);
    void removeInventory(Long inventoryId);
}
