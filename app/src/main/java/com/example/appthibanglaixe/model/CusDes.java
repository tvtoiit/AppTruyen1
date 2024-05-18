package com.example.appthibanglaixe.model;

import java.util.Date;

public class CusDes {
    private int idBo;
    private String title;
    private String image;
    private String description;
    private String author;
    private String publicationDate;
    private String content;

    // Constructor
    public CusDes(int idBo, String title, String image, String description, String author, String publicationDate, String content) {
        this.idBo = idBo;
        this.title = title;
        this.image = image;
        this.description = description;
        this.author = author;
        this.publicationDate = publicationDate;
        this.content = content;
    }

    public CusDes() {

    }

    // Getters
    public int getIdBo() {
        return idBo;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getContent() {
        return content;
    }
}
