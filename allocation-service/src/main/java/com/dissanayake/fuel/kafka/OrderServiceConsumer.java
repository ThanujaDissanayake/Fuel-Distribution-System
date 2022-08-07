package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.FuelStock;
import com.dissanayake.fuel.model.OrderEvent;
import com.dissanayake.fuel.repository.AllocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderServiceConsumer.class);
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;
    @Autowired
    AllocationRepository allocationRepository;

 /*   @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfiguration(), new StringSerializer(),
                new DelegatingByTypeSerializer(Map.of(byte[].class, new ByteArraySerializer(),
                        MyNormalObject.class, new JsonSerializer<Object>())));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }*/

    public OrderServiceConsumer(KafkaTemplate<String, OrderEvent> kafkaTemplate, AllocationRepository allocationRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.allocationRepository = allocationRepository;
    }

   // @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void reserve(OrderEvent orderEvent){
        LOGGER.info(String.format("Got an Order => %s",orderEvent.toString()));

        //Get object for the particular fuel type
        FuelStock fuelStock= allocationRepository.findByName(orderEvent.getType());
        //Check availability capacity for the relevant fuel tpe for order
        if(orderEvent.getCapacity()< fuelStock.getAvailableQuantity()){
            orderEvent.setStatus("ACCEPT");
            fuelStock.setReservedQuantity(fuelStock.getReservedQuantity()+orderEvent.getCapacity());
            fuelStock.setAvailableQuantity(fuelStock.getAvailableQuantity()-orderEvent.getCapacity());
        }
        else {
            orderEvent.setStatus("REJECTED");
        }
        //Update the stock data into DB by fuel type
        allocationRepository.save(fuelStock);

        //Send orderId and order event object to 'stock-order_topic'
        //Message<OrderEvent> message= MessageBuilder

        kafkaTemplate.send("stock-order_topic",orderEvent.getOrderId(),orderEvent);

        //kafkaTemplate.send("stock-order_topic",orderEvent.getOrderId(),orderEvent);
        LOGGER.info(String.format("Reserved the Order => %s",orderEvent.toString()));
    }

    //If order is dispatched, dispatch method will invoke.
    public void dispatch(OrderEvent orderEvent){
        LOGGER.info(String.format("Dispatch Method: => %s",orderEvent.toString()));
        FuelStock fuelStock= allocationRepository.findByName(orderEvent.getType());
        if(orderEvent.getStatus().equals("DISPATCHED")){
            fuelStock.setReservedQuantity(fuelStock.getReservedQuantity()-orderEvent.getCapacity());
            allocationRepository.save(fuelStock);
        }


    }
}
