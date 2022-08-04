package com.dissanayake.fuel.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    /*Fetch the topic name*/
//    @Value("${spring.kafka.topic.name}")
//    private String topicName;

    /*Spring been for kafka topics*/
    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder.name("order_topic").build();
    }

    @Bean
    public NewTopic stockTopic(){
        return TopicBuilder.name("stock-order_topic").build();
    }

    @Bean
    public NewTopic scheduleTopic(){
        return TopicBuilder.name("schedule-order_topic").build();
    }




}
