package com.bookstore_csa_cw.dao;

import com.bookstore_csa_cw.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderDAO {
    private static List<Order> orders = new ArrayList<>();
    private static int orderNo = 0;
    
    // Create an order 
    public static Order createOrder(int customerId, Map<Integer, Integer> items, double totalPrice) {
        int newOrderId = ++orderNo;
        Order order = new Order(newOrderId, customerId, items, totalPrice);        
        orders.add(order);         
        return order;
    }
    
    //All orders of a specific customer
    public static List<Order> getOrdersByCustomer(int customerId){
        List<Order> customerAllOrders = new ArrayList<>();
        for(Order order: orders){
            if(order.getCustomerId() == customerId){
                customerAllOrders.add(order);
            }     
        }
        return customerAllOrders;
    }
    
    //Get a specific order of a specific customer
    public static Order getSpecificOrder(int customerId, int orderId){
        List<Order> customerAllOrders = getOrdersByCustomer(customerId);
        for (Order order: customerAllOrders){
            if(order.getOrderId() == orderId){
                return order;
            }
        }
        return null;

    }
    
}
