package com.app.bookstore.repository;

import com.app.bookstore.model.Book;
import com.app.bookstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom
{
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public List<Book> findBooksByCategoryId(Long categoryId)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Book> book = criteriaQuery.from(Book.class);
        Predicate bookPredicate = criteriaBuilder.equal(book.get("categoryId"), categoryId);
        criteriaQuery.where(bookPredicate);

        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByBookstoreId(Long bookstoreId) throws Exception
    {
        List<Long> bookIDs = inventoryService.getBookIdsSellingByBookstore(bookstoreId);

        if(bookIDs.size() == 0)
            throw new Exception("Books cannot retrieved for Bookstore Id:" + bookstoreId );

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        Root<Book> book = criteriaQuery.from(Book.class);
        criteriaQuery.select(book)
                .where(book.get("id").in(bookIDs));

        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
