package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DispatchServiceProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(DispatchServiceProducer.class);
    private NewTopic disTopic;
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public DispatchServiceProducer(NewTopic disTopic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.disTopic = disTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("Order is Dispatched => %s",orderEvent.toString()));

       /* Message<OrderEvent> message= MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,disTopic.name())
                .build();*/

        kafkaTemplate.send("order_topic",orderEvent.getOrderId(),orderEvent);
    }
}
