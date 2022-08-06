package com.dissanayake.fuel;

import com.dissanayake.fuel.kafka.DispatchServiceProducer;
import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKafkaStreams
@EnableAsync
public class DispatchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatchServiceApplication.class, args);
	}

	@Autowired
	DispatchServiceProducer dispatchServiceProducer;

	@Bean
	public NewTopic dispatchTopic(){
		return TopicBuilder.name("dispatch-order_topic").build();
	}

	@Bean
	public KTable<String, OrderEvent> table(StreamsBuilder builder){
		KeyValueBytesStoreSupplier store =
				Stores.persistentKeyValueStore("schedule-order_topic");
		JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
		KStream<String, OrderEvent> stream = builder
				.stream("schedule-order_topic", Consumed.with(Serdes.String(), orderSerde));
		return stream.toTable(Materialized.<String, OrderEvent>as(store)
				.withKeySerde(Serdes.String())
				.withValueSerde(orderSerde));
	}
}
