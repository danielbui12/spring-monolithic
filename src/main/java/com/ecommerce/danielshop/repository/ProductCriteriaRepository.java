package com.ecommerce.danielshop.repository;

import com.ecommerce.danielshop.model.product.Product;
import com.ecommerce.danielshop.model.product.ProductPage;
import com.ecommerce.danielshop.model.product.ProductPageCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Product> findAllWithFilters(ProductPage productPage,
                                            ProductPageCriteria productPageCriteria){
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicate(productPageCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(productPage, criteriaQuery, productRoot);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(productPage.getPageNumber() * productPage.getPageSize());
        typedQuery.setMaxResults(productPage.getPageSize());

        Pageable pageable = getPageable(productPage);

        long count = getProductCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    private Predicate getPredicate(ProductPageCriteria productPageCriteria,
                                   Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(productPageCriteria.getBrand())){
            predicates.add(
                    criteriaBuilder.like(productRoot.get("brand"),
                            "%" + productPageCriteria.getBrand() + "%")
            );
        }
        if(Objects.nonNull(productPageCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(productRoot.get("name"),
                            "%" + productPageCriteria.getName() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ProductPage productPage,
                          CriteriaQuery<Product> criteriaQuery,
                          Root<Product> productRoot) {
        if (productPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
        }
    }

    private Pageable getPageable(ProductPage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(),employeePage.getPageSize(), sort);
    }

    private long getProductCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
