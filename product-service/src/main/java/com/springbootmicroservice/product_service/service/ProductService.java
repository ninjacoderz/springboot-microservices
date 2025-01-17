package com.springbootmicroservice.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springbootmicroservice.product_service.dto.ProductRequest;
import com.springbootmicroservice.product_service.dto.ProductResponse;
import com.springbootmicroservice.product_service.model.Product;
import com.springbootmicroservice.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        // Create a new product
        Product product = Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();
        productRepository.save(product);
        log.info("Product created: {}", product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Products found: {}", products);
        return products.stream()
            .map(product -> ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build())
            .toList();
    }
}
