/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.store;

/**
 *
 * @author ADMIN
 */
public class DataStore {
    public static Map<Integer, Book> books = new HashMap<>();
    public static Map<Integer, Author> authors = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();

    public static Order createOrder(int customerId, Order order) { ... }
    public static List<Order> getOrdersByCustomer(int customerId) { ... }
    public static Order getOrderById(int customerId, int orderId) { ... }
}

