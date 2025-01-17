package com.springbootmicroservice.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springbootmicroservice.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);

}
