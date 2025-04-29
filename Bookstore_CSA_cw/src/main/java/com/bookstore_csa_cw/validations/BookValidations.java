package com.bookstore_csa_cw.validations;

import com.bookstore_csa_cw.dao.AuthorDAO;
import com.bookstore_csa_cw.dao.BookDAO;
import com.bookstore_csa_cw.exception.AuthorNotFoundException;
import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.model.Author;
import com.bookstore_csa_cw.model.Book;
import java.time.LocalDate;

public class BookValidations {
    public static void validateBook(Book book, String message){
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty() ||
            book.getTitle() == null || book.getTitle().trim().isEmpty() ||
            book.getAuthor()== null || book.getAuthor().trim().isEmpty() ||
            book.getPublicationYear()== null || book.getPublicationYear().trim().isEmpty() ||
            book.getPrice() <= 0) {
            throw new InvalidInputException(message);
        }

        Author author = AuthorDAO.getAuthorById(book.getAuthorId());
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " does not exist.");
        }   
        
        if(Integer.parseInt(book.getPublicationYear()) > LocalDate.now().getYear()){
            throw new InvalidInputException("Publication year can't be in the future.");
        }
    }
}
