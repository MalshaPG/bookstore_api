package com.bookstore_csa_cw.model;

public class Customer {
    private int customerId;
    private String fName;
    private String lName;
    private String email;
    private String password;

    public Customer(){
    }
    
    public Customer(int customerId, String fName,String lName, String email, String password) {
        this.customerId = customerId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;   
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
