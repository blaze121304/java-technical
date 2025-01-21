package com.rusty.replication.repository;


import com.rusty.replication.common.search.PageSearchResult;
import com.rusty.replication.common.search.ProductSearchCriteria;
import com.rusty.replication.domain.model.Product;

public interface ProductRepositoryCustom {

    PageSearchResult<Product> search(ProductSearchCriteria criteria);
}
