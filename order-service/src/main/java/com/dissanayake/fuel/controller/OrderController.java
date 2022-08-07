package com.dissanayake.fuel.controller;

import com.dissanayake.fuel.kafka.OrderServiceProducer;
import com.dissanayake.fuel.model.Order;
import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/home")
public class OrderController {

    @Autowired
    private OrderServiceProducer orderServiceProducer;


    private StreamsBuilderFactoryBean kafkaStreamsFactory;


    public OrderController(OrderServiceProducer orderServiceProducer, StreamsBuilderFactoryBean kafkaStreamsFactory) {
        this.orderServiceProducer = orderServiceProducer;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
    }

    //Place an Order
    @CrossOrigin(origins = "http://localhost:4200/")
    @RequestMapping(value = "/orders",method = RequestMethod.POST)
    public String placeOrder(@RequestBody Order order){
        /*int orderId in Order model*/


        /*Create OrderEvent object*/
        OrderEvent orderEvent=new OrderEvent();
        orderEvent.setOrderId(UUID.randomUUID().toString());
        orderEvent.setStatus("NEW");
        orderEvent.setMessage("Got a new Order");
        orderEvent.setCapacity(order.getCapacity());
        orderEvent.setStationName(order.getStationName());
        orderEvent.setType(order.getType());

        /*Pass that to send message to Producer*/
        orderServiceProducer.sendMessage(orderEvent);

        return "Order Placed Successfully";
    }

    //Get all scheduled orders from schedule-order_topic
    @RequestMapping(value = "/allOrders",method = RequestMethod.GET)
    public List<OrderEvent> allScheduledOrders() {
        List<OrderEvent> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<String, OrderEvent> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(
                        "order-store",
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<String, OrderEvent> allOrders = store.all();
        allOrders.forEachRemaining(or -> orders.add(or.value));
        return orders;
    }


}
