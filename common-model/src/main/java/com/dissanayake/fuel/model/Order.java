package com.dissanayake.fuel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    //private String orderId;
    private String stationName;
    private int capacity;
    private String type;

//    private boolean status;


}
