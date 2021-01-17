package com.app.bookstore.repository;

import com.app.bookstore.model.Inventory;

import java.util.List;

public interface InventoryRepositoryCustom
{
    List<Long> findBookIdsSellingByBookstore(Long bookstoreId);
    Inventory findBookSellingByBookstore(Long bookId, Long bookstoreId);
}
