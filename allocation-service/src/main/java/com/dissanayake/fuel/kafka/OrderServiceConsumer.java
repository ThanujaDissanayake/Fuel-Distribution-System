package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.FuelStock;
import com.dissanayake.fuel.model.OrderEvent;
import com.dissanayake.fuel.repository.AllocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderServiceConsumer.class);
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;
    @Autowired
    AllocationRepository allocationRepository;

    public OrderServiceConsumer(KafkaTemplate<String, OrderEvent> kafkaTemplate, AllocationRepository allocationRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.allocationRepository = allocationRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void reserve(OrderEvent orderEvent){
        LOGGER.info(String.format("Got an Order => %s",orderEvent.toString()));

        FuelStock fuelStock= allocationRepository.findByName(orderEvent.getOrder().getType());
        if(orderEvent.getOrder().getCapacity()< fuelStock.getAvailableQuantity()){
            orderEvent.setStatus("ACCEPT");
            fuelStock.setReservedQuantity(fuelStock.getReservedQuantity()+orderEvent.getOrder().getCapacity());
            fuelStock.setAvailableQuantity(fuelStock.getAvailableQuantity()-orderEvent.getOrder().getCapacity());
        }
        else {
            orderEvent.setStatus("REJECTED");
        }
        //Save the order data into DB
        allocationRepository.save(fuelStock);

        /*Send */
        kafkaTemplate.send("stock-order_topic",orderEvent.getOrder().getOrderId(),orderEvent);
        LOGGER.info(String.format("Reserved the Order => %s",orderEvent.toString()));
    }
}
