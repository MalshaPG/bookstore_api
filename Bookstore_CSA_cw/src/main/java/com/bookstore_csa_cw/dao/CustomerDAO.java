/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore_csa_cw.dao;

import com.bookstore_csa_cw.model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @customer Dell
 */
public class CustomerDAO {
    private static List<Customer> customers = new ArrayList<>();
    private static int customerIdCounter = 0;

    public static List<Customer> getAllCustomers() {
        return customers;
    }

    public static Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == id) {
                return customer;
            }
        }
        return null;
    }

    public static Customer addCustomer(Customer customer) {
        customer.setCustomerId(++customerIdCounter);
        customers.add(customer);
        return customer;
    }

    public static boolean updateCustomer(int id, Customer updatingCustomer) {
        for (Customer customer: customers) {
            if(customer.getCustomerId() == id){
                customer.setfName(updatingCustomer.getfName());
                customer.setlName(updatingCustomer.getlName());
                customer.setEmail(updatingCustomer.getEmail());
                customer.setPassword(updatingCustomer.getPassword());
                return true;
            }
        }
        return false;
    }

    public static boolean deleteCustomer(int id) {
        return customers.removeIf(customer -> customer.getCustomerId() == id);
    }
}
