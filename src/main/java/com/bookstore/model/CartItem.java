
package com.bookstore.model;


public class CartItem {
    private Book book;
    private int quantity;

    // Default constructor
    public CartItem() {
        // You can initialize with default values if needed, or leave it empty
        this.book = new Book();  // assuming Book has a default constructor
        this.quantity = 1; // Default quantity
    }

    // Constructor with parameters
    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}



