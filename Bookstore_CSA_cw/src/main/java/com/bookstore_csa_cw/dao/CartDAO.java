package com.bookstore_csa_cw.dao;

import com.bookstore_csa_cw.model.Cart;

import java.util.HashMap;
import java.util.Map;

public class CartDAO {
//    Map<customerId, cart>
    private static Map<Integer, Cart> carts = new HashMap<>();
    
    public static boolean checkCustomerCart(int customerId, int bookId){
        if(carts.containsKey(customerId)){
            Cart cart = carts.get(customerId);
            if (cart.checkItemInCart(bookId)){
                return true;
            }
        }
        return false;
    }
    
    public static void addCart(int customerId, Cart cart){
        carts.put(customerId, cart);
    }    
                  
    public static Cart getCart(int customerId) {
        return carts.get(customerId);
    }

    public static boolean deleteCart(int customerId) {
        return carts.remove(customerId) != null;
    }
}
