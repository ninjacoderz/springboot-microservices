package com.springbootmicroservice.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener (topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent){
		// send out an email notification
		// send some SMS, iMessage etc... here

		log.info("Received Notification for Order - {}", orderPlacedEvent.getOrderNumber());

	}
}
