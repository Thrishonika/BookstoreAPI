/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.store;

import com.bookstore.model.Book;
import com.bookstore.model.Author;
import com.bookstore.model.Customer;
import com.bookstore.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class DataStore {
    public static Map<Integer, Book> books = new HashMap<>();
    public static Map<Integer, Author> authors = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();

    public static Order createOrder(int customerId, Order order) {
        // Dummy implementation for now
        return order;
    }

    public static List<Order> getOrdersByCustomer(int customerId) {
        // Dummy implementation for now
        return new ArrayList<>();
    }

    public static Order getOrderById(int customerId, int orderId) {
        // Dummy implementation for now
        return null;
    }
}


