package com.dissanayake.fuel;

import com.dissanayake.fuel.kafka.OrderServiceConsumer;
import com.dissanayake.fuel.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class AllocationServiceApplication {

	private static final Logger LOG = LoggerFactory.getLogger(AllocationServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AllocationServiceApplication.class, args);
	}

	@Autowired
	OrderServiceConsumer orderServiceConsumer;

	//Listen events form 'orders' Topic and consume data
	@KafkaListener(id = "order_topic", topics = "order_topic", groupId = "stock")
	public void onEvent(OrderEvent orderEvent) {
		LOG.info("Received an Order: {}" , orderEvent.toString());
		if (orderEvent.getStatus().equals("NEW"))
			orderServiceConsumer.reserve(orderEvent);
		else
			orderServiceConsumer.dispatch(orderEvent);
	}
}
