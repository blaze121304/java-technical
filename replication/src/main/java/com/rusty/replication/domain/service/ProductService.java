package com.rusty.replication.domain.service;

import com.rusty.replication.common.search.PageSearchResult;
import com.rusty.replication.common.search.ProductSearchCriteria;
import com.rusty.replication.domain.dto.ProductDto;
import java.util.List;

public interface ProductService {

    ProductDto findById(Long id);

    List<ProductDto> findAll();

    ProductDto save(ProductDto productDto);

    void deleteById(Long id);

    PageSearchResult<ProductDto> search(ProductSearchCriteria criteria);
}
