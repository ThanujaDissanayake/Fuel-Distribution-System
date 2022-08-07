package com.dissanayake.fuel.kafka;

import com.dissanayake.fuel.model.OrderEvent;
import com.dissanayake.fuel.model.ScheduledOrder;
import com.dissanayake.fuel.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class AllocationServiceConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(AllocationServiceConsumer.class);
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    @Autowired
    ScheduleRepository scheduleRepository;

    public AllocationServiceConsumer(KafkaTemplate<String, OrderEvent> kafkaTemplate, ScheduleRepository scheduleRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.scheduleRepository = scheduleRepository;
    }

    @KafkaListener(topics = "stock-order_topic", groupId = "${spring.kafka.consumer.group-id}")
    public void scheduleOrder(OrderEvent orderEvent){
        LOGGER.info(String.format("Got an Confirmed Order to Schedule => %s",orderEvent.toString()));

        //Set schedule date for the confirmed order
        if(orderEvent.getStatus().equals("ACCEPT")){
            orderEvent.setScheduledDate(generateDate());
            scheduleRepository.save(new ScheduledOrder(
                    orderEvent.getOrderId(),
                    orderEvent.getStationName(),
                    orderEvent.getCapacity(),
                    orderEvent.getType(),
                    orderEvent.getStatus(),
                    orderEvent.getScheduledDate()
            ));

            kafkaTemplate.send("schedule-order_topic",orderEvent.getOrderId(),orderEvent);
        }
        LOGGER.info(String.format("Order is Scheduled on => %s",orderEvent.getScheduledDate()));
    }

    public static Date generateDate(){
        Instant randDate=Instant.now().plus(Duration.ofDays(7));
        return Date.from(randDate);
    }
}
