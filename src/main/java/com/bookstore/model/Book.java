package com.bookstore.model;

/**
 *
 * @author ADMIN
 */
public class Book {
         private int id;
         private String title;
         private String author;
         private String isbn;
         private int publicationYear;
         private double price;
         private int stockQuantity;
        private int authorId; // make sure this exists
        private int stock;

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }


        // Constructor(s)
       public Book() {} // Default constructor (for JSON)
       public Book(int id, String title, String author, String isbn, int year, double price, int stock) {
           this.id = id;
           this.title = title;
           this.author = author;
           this.isbn = isbn;
           this.publicationYear = year;
           this.price = price;
           this.stockQuantity = stock;
            this.stock = stock;
       }

       // Getters and Setters
       public int getId() { return id; }
       public void setId(int id) { this.id = id; }

       public String getTitle() { return title; }
       public void setTitle(String title) { this.title = title; }

       public String getAuthor() { return author; }
       public void setAuthor(String author) { this.author = author; }

       public String getIsbn() { return isbn; }
       public void setIsbn(String isbn) { this.isbn = isbn; }

       public int getPublicationYear() { return publicationYear; }
       public void setPublicationYear(int year) { this.publicationYear = year; }

       public double getPrice() { return price; }
       public void setPrice(double price) { this.price = price; }

       public int getStockQuantity() { return stockQuantity; }
       public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
         // Add this getter for stock
    public int getStock() {
        return stock;
    }

    //  (Optional) If you allow updating stock
    public void setStock(int stock) {
        this.stock = stock;
    }
       
       @Override
        public String toString() {
        return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", isbn='" + isbn + '\'' +
            ", publicationYear=" + publicationYear +
            ", price=" + price +
            ", stockQuantity=" + stockQuantity +
            '}';
}

}

