package com.springbootmicroservice.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.springbootmicroservice.order_service.dto.InventoryResponse;
import com.springbootmicroservice.order_service.dto.OrderRequest;
import com.springbootmicroservice.order_service.event.OrderPlacedEvent;
import com.springbootmicroservice.order_service.model.Order;
import com.springbootmicroservice.order_service.model.OrderLineItems;
import com.springbootmicroservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    final private OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());  
        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItemsDto -> {
            return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
        }).toList();

        order.setOrderLineItemsList(orderLineItems);

        // Call Inventory Service to check the availability of the products
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
            .uri("lb://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
            .build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

        boolean result = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());
        if(result) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        } else {
            throw new IllegalArgumentException("Product is not available");
        }
    }
}
