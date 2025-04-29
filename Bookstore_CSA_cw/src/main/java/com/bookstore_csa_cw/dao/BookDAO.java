package com.bookstore_csa_cw.dao;

import com.bookstore_csa_cw.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BookDAO {
    private static List<Book> books = new ArrayList<>();
    private static int bookIdCounter = 0;

    public static List<Book> getAllBooks() {
        return books;
    }

    public static Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static boolean isISBNUnique(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return false;
            }
        }
        return true;
    }

    public static Book addBook(Book book) {
        book.setId(++bookIdCounter);
        books.add(book);
        return book;
    }

    public static boolean updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                updatedBook.setId(id); // 
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
    
    public static List<Book> getBooksByAuthorId(int authorId) {
        List<Book> authorBooks = new ArrayList<>();
       
        for(Book book:books){
            if(book.getAuthorId() == authorId){
                authorBooks.add(book);
            }
        }  
        return authorBooks;
    }
}


