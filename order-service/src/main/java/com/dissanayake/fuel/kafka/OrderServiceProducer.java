package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderServiceProducer.class);


    private NewTopic topic;

    /*Kafka Template to send and event/message to the Kafka Topic*/
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    /*Dependency injection to new topic in Kafka template classes*/
    public OrderServiceProducer( KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("Order Event => %s",orderEvent.toString()));

        /*Using KafkaTemplate send the message to Kafka topic */
        kafkaTemplate.send("order_topic",orderEvent.getOrderId(),orderEvent);
    }
}
