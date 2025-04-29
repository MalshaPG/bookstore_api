package com.bookstore_csa_cw.model;

public class Book {
    private int id;
    private String isbn; 
    private String title;
    private String author; 
    private int authorId;
    private String publicationYear;
    private double price;
    private int stockQuantity;
    
    public Book() {
    }

    public Book( int id, String isbn, String title, String author, int authorId, String publicationYear, double price, int stockQuantity) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String ISBN) {
        this.isbn = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }  

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear + ", price=" + price + ", stockQuantity=" + stockQuantity + '}';
    }
    
    
}
