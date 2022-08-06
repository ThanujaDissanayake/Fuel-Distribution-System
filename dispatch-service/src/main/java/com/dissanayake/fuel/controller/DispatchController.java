package com.dissanayake.fuel.controller;

import com.dissanayake.fuel.kafka.DispatchServiceProducer;
import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.apache.kafka.streams.state.QueryableStoreTypes.keyValueStore;

@RestController
@RequestMapping(value = "/scheduledOrders")
public class DispatchController {

    private static final Logger LOG = LoggerFactory.getLogger(DispatchController.class);
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private StreamsBuilderFactoryBean streamFactory;

    @Autowired
    private DispatchServiceProducer dispatchServiceProducer;

    public DispatchController(KafkaTemplate<String, OrderEvent> kafkaTemplate, StreamsBuilderFactoryBean streamFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.streamFactory = streamFactory;
    }

    @RequestMapping(value = "/allOrders",method = RequestMethod.GET)
    public List<OrderEvent> allOrders(){
        List<OrderEvent> orders=new ArrayList<>();

        ReadOnlyKeyValueStore<String,OrderEvent> store=streamFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(
                        "schedule-order_topic",
                        keyValueStore()
                ));
        KeyValueIterator<String,OrderEvent> list=store.all();
        list.forEachRemaining(object -> orders.add(object.value));

        return orders;
    }

    @RequestMapping(value = "/dispatch",method = RequestMethod.POST)
    public void dispatchOrder(@RequestBody OrderEvent disOrder){
        if(disOrder.getStatus().equals("DISPATCHED")){
            disOrder.setMessage("Order is dispatched");
            dispatchServiceProducer.sendMessage(disOrder);
        }
    }
}
