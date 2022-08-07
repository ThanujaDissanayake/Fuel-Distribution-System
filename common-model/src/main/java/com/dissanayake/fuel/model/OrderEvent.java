package com.dissanayake.fuel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*Create class to transfer the data between producer(OrderService) and consumer(Allocation Service)*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private String orderId;
    private String message;
    private String status;
    private String stationName;
    private int capacity;
    private String type;
    private Date scheduledDate;
}
