package com.springbootmicroservice.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springbootmicroservice.inventory_service.model.Inventory;
import com.springbootmicroservice.inventory_service.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository repositoryRepository) {

		return args -> {
			System.out.println("Inventory Service is up and running...");
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iPhone_13");
			inventory.setQuantity(100);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iPhone_13_red");
			inventory1.setQuantity(0);
			
			repositoryRepository.save(inventory);
			repositoryRepository.save(inventory1);
		};
	}
}
