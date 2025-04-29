/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore_csa_cw.dao;

import com.bookstore_csa_cw.model.Author;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class AuthorDAO {
    private static List<Author> authors = new ArrayList<>();
    private static int authorIdCounter = 0;

    public static List<Author> getAllAuthors() {
        return authors;
    }

    public static Author getAuthorById(int id) {
        for (Author author : authors) {
            if (author.getAuthorId() == id) {
                return author;
            }
        }
        return null;
    }

    public static Author addAuthor(Author author) {
        author.setAuthorId(++authorIdCounter);
        authors.add(author);
        return author;
    }

    public static boolean updateAuthor(int id, Author updatingAuthor) {
        for (Author author: authors) {
            if(author.getAuthorId() == id){
                author.setfName(updatingAuthor.getfName());
                author.setlName(updatingAuthor.getlName());
                author.setBiography(updatingAuthor.getBiography());
                
                return true;
            }
        }
        return false;
    }

    public static boolean deleteAuthor(int id) {
        return authors.removeIf(author -> author.getAuthorId() == id);
    }
}
