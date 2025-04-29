package com.bookstore_csa_cw.model;

public class Author {
    private int authorId;
    private String fName;
    private String lName;
    private String biography;

    public Author() {
    }    
    
//    Constructor
    public Author(int authorId, String fName, String lName, String biography) {
        this.authorId = authorId;
        this.fName = fName;
        this.lName = lName;
        this.biography = biography;
    }

//    Getters and Setters
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
    
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
}
