package com.app.bookstore.repository;

import com.app.bookstore.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InventoryRepositoryCustomImpl implements InventoryRepositoryCustom
{
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Long> findBookIdsSellingByBookstore(Long bookstoreId)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> criteriaQuery = criteriaBuilder.createQuery(Inventory.class);

        Root<Inventory> inventory = criteriaQuery.from(Inventory.class);
        Predicate bookstorePredicate = criteriaBuilder.equal(inventory.get("bookstoreId"), bookstoreId);
        criteriaQuery.where(bookstorePredicate);

        TypedQuery<Inventory> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .stream()
                .map(Inventory::getBookId)
                .collect(Collectors.toList());
    }

    @Override
    public Inventory findBookSellingByBookstore(Long bookId, Long bookstoreId)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> criteriaQuery = criteriaBuilder.createQuery(Inventory.class);

        Root<Inventory> inventory = criteriaQuery.from(Inventory.class);
        Predicate bookstorePredicate = criteriaBuilder.equal(inventory.get("bookstoreId"), bookstoreId);
        Predicate bookPredicate = criteriaBuilder.equal(inventory.get("bookId"), bookId);
        Predicate predicateForBookExistenceInBookstore = criteriaBuilder.and(bookPredicate,bookstorePredicate);
        criteriaQuery.where(predicateForBookExistenceInBookstore);

        TypedQuery<Inventory> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
