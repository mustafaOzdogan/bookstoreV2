package com.app.bookstore.repository;

import com.app.bookstore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {}
