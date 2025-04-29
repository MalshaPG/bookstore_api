package com.bookstore_csa_cw.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Order {
    private int orderId;
    private int customerId;
    private Map<Integer, Integer> items; 
    private double totalAmount;
    private String orderDate;
    
    public Order(){
    }

    public Order(int orderId, int customerId, Map<Integer, Integer> items, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate =  LocalDate.now().format(DateTimeFormatter.ISO_DATE); 
    }

    // Getters and Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    
}
