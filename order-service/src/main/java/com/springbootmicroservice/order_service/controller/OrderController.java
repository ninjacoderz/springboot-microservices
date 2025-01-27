package com.springbootmicroservice.order_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;

import com.springbootmicroservice.order_service.dto.OrderRequest;
import com.springbootmicroservice.order_service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/order")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing Order{}", orderRequest);
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest)) ;
    }

    public CompletableFuture<String> fallbackMethod( OrderRequest orderRequest, RuntimeException exception){
        log.info("Cannot Place Order Executing Fallback logic {}", orderRequest);
        return CompletableFuture.supplyAsync(() -> "Oops, Something went wrong, please take order again sometimes!");
    }
}
