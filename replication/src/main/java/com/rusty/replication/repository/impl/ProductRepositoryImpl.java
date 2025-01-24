package com.rusty.replication.repository.impl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.*;
import com.rusty.replication.common.search.PageSearchResult;
import com.rusty.replication.common.search.ProductSearchCriteria;
import com.rusty.replication.common.search.QueryExecutor;
import com.rusty.replication.common.utils.SearchUtils;
import com.rusty.replication.controller.enums.MeasurementUnit;
import com.rusty.replication.domain.model.Product;
import com.rusty.replication.repository.ProductRepositoryCustom;
import io.micrometer.common.util.StringUtils;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashMap;

public class ProductRepositoryImpl extends QueryExecutor implements ProductRepositoryCustom {

    private static final QProduct qProduct = QProduct.product;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageSearchResult<Product> search(ProductSearchCriteria criteria) {
        JPAQuery<Product> query = new JPAQuery<Product>(entityManager)
                .from(qProduct)
                .where(predicateOf(criteria))
                .orderBy(SearchUtils.orderSpecifierOf(criteria, orderMap(), ProductSearchCriteria.DEFAULT_ORDER_BY));

        return super.executeQuery(criteria, query);
    }

    private Predicate predicateOf(ProductSearchCriteria criteria) {
        BooleanBuilder predicate = new BooleanBuilder();

        Long id = criteria.getId();
        if(id != null)
            predicate.and(qProduct.id.eq(criteria.getId()));

        String code = criteria.getCode();
        if(StringUtils.isNotBlank(code))
            predicate.and(qProduct.code.containsIgnoreCase(code));

        MeasurementUnit measurementUnit = criteria.getMeasurementUnit();
        if(measurementUnit != null)
            predicate.and(qProduct.measurementUnit.eq(measurementUnit));

        String description = criteria.getDescription();
        if(StringUtils.isNotBlank(description))
            predicate.and(qProduct.description.containsIgnoreCase(description));

        return predicate;
    }

    private HashMap<String, Path> orderMap() {
        HashMap<String, Path> map = new HashMap<>();

        map.put(ProductSearchCriteria.ORDER_BY_ID, qProduct.id);
        map.put(ProductSearchCriteria.ORDER_BY_CODE, qProduct.code);
        map.put(ProductSearchCriteria.ORDER_BY_MEASUREMENT_UNIT, qProduct.measurementUnit);

        return map;
    }
}
