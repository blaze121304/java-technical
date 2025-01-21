package com.rusty.replication.domain.service.impl;


import com.rusty.replication.common.exception.ResourceNotFoundException;
import com.rusty.replication.common.search.PageSearchResult;
import com.rusty.replication.common.search.ProductSearchCriteria;
import com.rusty.replication.common.validation.ProductDtoValidator;
import com.rusty.replication.domain.dto.ProductDto;
import com.rusty.replication.domain.model.Product;
import com.rusty.replication.domain.service.ProductService;
import com.rusty.replication.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoValidator productDtoValidator;

    public ProductServiceImpl(ProductRepository productRepository, ProductDtoValidator productDtoValidator) {
        this.productRepository = productRepository;
        this.productDtoValidator = productDtoValidator;
    }


    @Override
    public ProductDto findById(Long id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return new ProductDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> new ProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        productDtoValidator.validate(productDto);
        Product product = this.dtoToEntity(productDto);
        Product savedProduct = this.productRepository.save(product);
        return new ProductDto(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public PageSearchResult<ProductDto> search(ProductSearchCriteria criteria) {
        PageSearchResult<Product> page = this.productRepository.search(criteria);
        List<ProductDto> dtos = page
                .getPageData()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

        return new PageSearchResult<>(page.getTotalRows(), dtos);
    }

    private Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
