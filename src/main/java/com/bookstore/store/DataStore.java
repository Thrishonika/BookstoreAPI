package com.bookstore.store;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.model.Author;
import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
import com.bookstore.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    public static Map<Integer, Book> books = new HashMap<>();
    public static Map<Integer, Author> authors = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();
    public static Map<Integer, List<CartItem>> customerCarts = new HashMap<>();
    public static Map<Integer, Order> orders = new HashMap<>();

    private static AtomicInteger orderIdCounter = new AtomicInteger(1);  // Atomic counter for order IDs
   
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

    public static boolean updateCartItem(int customerId, int bookId, CartItem updatedItem) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getBook().getId() == bookId) {
                    item.setQuantity(updatedItem.getQuantity());
                    return true;
                }
            }
        }
        return false;
    }

    public static void clearCart(int customerId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            cart.clear();
        }
    }

    public static Order createOrder(int customerId, List<CartItem> items) {
        // Generate a new order ID
        int newId = orderIdCounter.getAndIncrement();
        double totalPrice = items.stream().mapToDouble(item -> item.getBook().getPrice() * item.getQuantity()).sum();
        Order order = new Order(newId, customerId, items, totalPrice);
        orders.put(newId, order);
        return order;
    }
    public static int getStockForBook(int bookId) {
        // Logic to get the stock of a book from your book store map
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        }
        return book.getStock();
    }


    public static List<Order> getOrdersByCustomer(int customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId() == customerId) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public static Order getOrderById(int customerId, int orderId) {
        Order order = orders.get(orderId);
        if (order != null && order.getCustomerId() == customerId) {
            return order;
        }
        return null;  // Return null if the order is not found or doesn't belong to the customer
    }
}