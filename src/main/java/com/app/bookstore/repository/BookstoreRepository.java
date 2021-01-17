package com.app.bookstore.repository;

import com.app.bookstore.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
}
