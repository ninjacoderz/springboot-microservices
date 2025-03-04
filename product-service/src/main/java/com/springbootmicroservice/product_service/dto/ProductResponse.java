package com.springbootmicroservice.product_service.dto;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
}
