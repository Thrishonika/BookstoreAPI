
package com.bookstore.store;

import com.bookstore.model.Book;
import com.bookstore.model.Author;
import com.bookstore.model.CartItem;
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
    public static Map<Integer, List<CartItem>> customerCarts = new HashMap<>();

    public static List<CartItem> getCart(int customerId) {
        return customerCarts.getOrDefault(customerId, new ArrayList<>());
    }

    public static void addCartItem(int customerId, CartItem item) {
        customerCarts.computeIfAbsent(customerId, k -> new ArrayList<>()).add(item);
    }

    public static void removeCartItem(int customerId, int bookId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            cart.removeIf(ci -> ci.getBook().getId() == bookId);
        }
    }

    public static void clearCart(int customerId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            cart.clear();
        }
    }

    public static Map<Integer, Order> orders = new HashMap<>();  // Add this line to store orders

    public static Order createOrder(int customerId, Order order) {
        // Assuming orders are uniquely identified by their ID
        int newId = orders.size() + 1;  // Generate a new ID for the order
        order.setId(newId);  // Set the ID of the order
        orders.put(newId, order);  // Add the order to the orders map
        return order;
    }

    public static List<Order> getOrdersByCustomer(int customerId) {
        // Return orders based on customer ID
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public static Order getOrderById(int customerId, int orderId) {
        // Retrieve a specific order by customer and order ID
        Order order = orders.get(orderId);
        if (order != null && order.getCustomerId() == customerId) {
            return order;
        }
        return null;  // Return null if the order is not found or doesn't belong to the customer
    }
}