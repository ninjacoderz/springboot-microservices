package com.springbootmicroservice.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootmicroservice.inventory_service.dto.InventoryResponse;
import com.springbootmicroservice.inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInstock(List<String> skuCode) {

        // For simulate slow query

        // log.info(" Wait Started ");
        // try {
        //     Thread.sleep(10000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // log.info(" Wait End");

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
        .map(inventory -> InventoryResponse.builder()
            .skuCode(inventory.getSkuCode())
            .isInStock(inventory.getQuantity() > 0)
            .build()).toList();
    }
}
