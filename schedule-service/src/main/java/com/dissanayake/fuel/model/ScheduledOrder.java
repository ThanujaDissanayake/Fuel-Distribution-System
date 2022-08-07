package com.dissanayake.fuel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scheduledOrder")
public class ScheduledOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String orderId;
    private String stationName;
    private int capacity;
    private String type;
    private String status;
    private Date scheduledDate;

    public ScheduledOrder() {
    }

    public ScheduledOrder(int id, String orderId, String stationName, int capacity, String type, String status, Date scheduledDate) {
        this.id = id;
        this.orderId = orderId;
        this.stationName = stationName;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
        this.scheduledDate = scheduledDate;
    }

    public ScheduledOrder(String orderId, String stationName, int capacity, String type, String status, Date scheduledDate) {
        this.orderId = orderId;
        this.stationName = stationName;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
        this.scheduledDate = scheduledDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}
