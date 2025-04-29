package com.bookstore_csa_cw.validations;

import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.model.Author;


public class AuthorValidations {
    public static void validateAuthor(Author author, String message){
        if (author.getfName() == null || author.getfName().trim().isEmpty() ||
            author.getlName() == null || author.getlName().trim().isEmpty() ||
            author.getBiography() == null || author.getBiography().trim().isEmpty()) {
            throw new InvalidInputException(message);
        }
    }
}
