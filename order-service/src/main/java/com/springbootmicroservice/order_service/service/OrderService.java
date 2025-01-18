package com.springbootmicroservice.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springbootmicroservice.order_service.dto.OrderRequest;
import com.springbootmicroservice.order_service.model.Order;
import com.springbootmicroservice.order_service.model.OrderLineItems;
import com.springbootmicroservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    final private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
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
        orderRepository.save(order);
    }
}
