package com.bookstore_csa_cw.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    // Map<bookId, quantity>
    private Map<Integer, Integer> items = new HashMap<>();
    
    public Cart(){
    }
    
    public Cart(Map<Integer, Integer> items) {
        this.items = items;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void addItem(int bookId, int quantity) {
        items.put(bookId, quantity);
    }

    public boolean updateItem(int bookId, int quantity) {
        if (items.containsKey(bookId)) {
            items.put(bookId, quantity);
            return true;
        }
        return false;
    }

    public boolean removeItem(int bookId) {
        if(!items.isEmpty() && items.containsKey(bookId)){
            items.remove(bookId) ;
            return true;
        }
        return false;
    }

    public boolean checkItemInCart(int bookId){
        return items.containsKey(bookId);        
    }
}
