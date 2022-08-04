package com.dissanayake.fuel.controller;

import com.dissanayake.fuel.kafka.OrderServiceProducer;
import com.dissanayake.fuel.model.Order;
import com.dissanayake.fuel.model.OrderEvent;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/home")
public class OrderController {

    private OrderServiceProducer orderServiceProducer;

    public OrderController(OrderServiceProducer orderServiceProducer) {
        this.orderServiceProducer = orderServiceProducer;
    }

    @RequestMapping(value = "/orders",method = RequestMethod.POST)
    public String placeOrder(@RequestBody Order order){
        /*int orderId in Order model*/
        order.setOrderId(UUID.randomUUID().toString());

        /*Create OrderEvent object*/
        OrderEvent orderEvent=new OrderEvent();
        orderEvent.setStatus("NEW");
        orderEvent.setMessage("Got a new Order");
        orderEvent.setOrder(order);

        /*Pass that to send message to Producer*/
        orderServiceProducer.sendMessage(orderEvent);

        return "Order Placed Successfully";
    }
}
