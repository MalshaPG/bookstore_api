package com.bookstore_csa_cw.validations;

import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.model.Customer;


public class CustomerValidations {
    public static void validateCustomer(Customer customer, String message){
        if (customer.getfName() == null || customer.getfName().trim().isEmpty() ||
            customer.getlName() == null || customer.getlName().trim().isEmpty() ||
            customer.getEmail() == null || customer.getEmail().trim().isEmpty() ||
            customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new InvalidInputException(message);
        }
    }
}
