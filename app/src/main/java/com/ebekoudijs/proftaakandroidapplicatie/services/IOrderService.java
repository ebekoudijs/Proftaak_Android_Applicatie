package com.ebekoudijs.proftaakandroidapplicatie.services;

import com.ebekoudijs.proftaakandroidapplicatie.Product;

import java.util.List;

public interface IOrderService {
    List<Product> getDrinks();
    Order addOrder(Order order);
}
