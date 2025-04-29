/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore_csa_cw.exception;


/**
 *
 * @author Dell
 */
public class CartNotFoundException extends RuntimeException{
   public  CartNotFoundException(String message){
       super (message);
   }
}
