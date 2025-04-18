/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.model;

/**
 *
 * @author ADMIN
 */
import java.util.List;
import java.time.LocalDate;

public class Order {
     private int id;
     private int customerId;
     private List<CartItem> items;
     private double totalPrice;
     private LocalDate orderDate;


    public Order() {}

    public Order(int id, int customerId, List<CartItem> items, double totalPrice, LocalDate orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    @Override
    public String toString() {
    return "Order{" +
            "id=" + id +
            ", customerId=" + customerId +
            ", items=" + items +
            ", totalPrice=" + totalPrice +
            ", orderDate=" + orderDate +
            '}';
}

}

