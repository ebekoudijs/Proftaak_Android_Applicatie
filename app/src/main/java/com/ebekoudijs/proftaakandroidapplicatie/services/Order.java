package com.ebekoudijs.proftaakandroidapplicatie.services;

import com.ebekoudijs.proftaakandroidapplicatie.ProductLine;
import com.ebekoudijs.proftaakandroidapplicatie.User;

import java.util.ArrayList;

public class Order {
    public String Message;
    public User User;
    public int Table;
    public ArrayList<ProductLine> OrderLines;

    public Order(String message, User user, int table, ArrayList<ProductLine> orderLines){
        Message = message;
        User = user;
        Table = table;
        OrderLines = orderLines;
    }

    public double totalPrice(){
        return 0;
    }
}
