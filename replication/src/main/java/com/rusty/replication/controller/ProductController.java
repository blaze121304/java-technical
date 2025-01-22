package com.rusty.replication.controller;


import com.rusty.replication.common.search.PageSearchResult;
import com.rusty.replication.common.search.ProductSearchCriteria;
import com.rusty.replication.common.search.SearchRequest;
import com.rusty.replication.common.utils.SearchUtils;
import com.rusty.replication.domain.dto.ProductDto;
import com.rusty.replication.domain.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //특정 ID로 제품 조회.
    @GetMapping("{id}")
    public ProductDto findById(@PathVariable Long id) {
        return this.productService.findById(id);
    }

    //전체 제품 목록 조회.
    @GetMapping
    public List<ProductDto> findAll() {
        return this.productService.findAll();
    }

    //새로운 제품 추가.
    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        return this.productService.save(productDto);
    }

    //기존 제품 정보 수정.
    @PutMapping
    public ProductDto update(@RequestBody ProductDto productDto) {
        return this.productService.save(productDto);
    }

    //특정 ID의 제품 삭제.
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
    }

    //조건에 따른 제품 검색.
    @PostMapping("search")
    public Page<ProductDto> search(@RequestBody SearchRequest searchRequest) {
        ProductSearchCriteria criteria = SearchUtils.createSearchCriteria(searchRequest, ProductSearchCriteria.class);
        PageSearchResult<ProductDto> pageSearchResult = this.productService.search(criteria);
        return SearchUtils.pageOf(searchRequest, pageSearchResult);
    }

}
