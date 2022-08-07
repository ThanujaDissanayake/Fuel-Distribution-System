package com.dissanayake.fuel.config;

import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
@Async
public class KafkaTopicConfiguration {

    /*Fetch the topic name*/
//    @Value("${spring.kafka.topic.name}")
//    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicConfiguration.class);
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

    @Bean
    public KStream<String, OrderEvent> stream(StreamsBuilder builder) {
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<String, OrderEvent> stream = builder
                .stream("stock-order_topic", Consumed.with(Serdes.String(), orderSerde));

        stream.join(
                builder.stream("schedule-order_topic"),
                (value1, value2) -> "left=" + value1 + ", right=" + value2,
                        JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(Serdes.String(), orderSerde, orderSerde))
                .peek((k, o) -> LOGGER.info("Created Join Stream",o))
                .to("order_topic");

        return stream;
    }

    @Bean
    public KTable<String, OrderEvent> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store =
                Stores.persistentKeyValueStore("order-store");
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<String, OrderEvent> stream = builder
                .stream("order_topic", Consumed.with(Serdes.String(), orderSerde));
        return stream.toTable(Materialized.<String, OrderEvent>as(store)
                .withKeySerde(Serdes.String())
                .withValueSerde(orderSerde));
    }
}
