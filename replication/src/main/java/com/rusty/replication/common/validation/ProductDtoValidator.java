package com.rusty.replication.common.validation;

import com.rusty.replication.domain.dto.ProductDto;
import com.rusty.replication.domain.model.Product;
import com.rusty.replication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoValidator implements BaseValidator<ProductDto> {

    private static final String FIELD_CODE = "code";

    private final ProductRepository productRepository;
    private final CommonValidatorUtils<ProductDto, Product> commonValidatorUtils;

    @Autowired
    public ProductDtoValidator(ProductRepository productRepository, CommonValidatorUtils<ProductDto, Product>  commonValidatorUtils) {
        this.productRepository = productRepository;
        this.commonValidatorUtils = commonValidatorUtils;
    }

    @Override
    public void validate(ProductDto obj) {
        //에러로 처리해도 된다.
        validateUniqueCode(obj);
    }

    private void validateUniqueCode(ProductDto productDto) {

        //데이터베이스에서 해당 코드에 대한 기존 레코드를 검색.
        Product product = this.productRepository
                .findByCode(productDto.getCode())
                .orElse(null);

        // Found product with the given code
        if (product != null) {
            commonValidatorUtils.validateUniqueField(FIELD_CODE, productDto, product);
        }

    }
}
