package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderServiceProducer.class);
    private NewTopic topic;

    /*Kafka Template to send and event/message to the Kafka Topic*/
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    /*Dependency injection to new topic in Kafka template classes*/
    public OrderServiceProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("Order Event => %s",orderEvent.toString()));

        /*Send a message to Kafka topic using Kafka template*/

        //Create a message
        Message<OrderEvent> message= MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name())
                .build();

        /*Using KafkaTemplate to send the message Kafka topic */
        kafkaTemplate.send(message);
    }
}
