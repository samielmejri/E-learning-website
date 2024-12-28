package com.example.mongodb_springboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {

    @Id
    private String id;
    private String title;
    private String image; // Stores the image as a Base64 string or file path
    private double price;

    // Default constructor
    public Course() {
    }

    // Parameterized constructor
    public Course(String title) {
        this.title = title;
    }

    public Course(String title, String image, double price) {
        this.title = title;
        this.image = image;
        this.price = price;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
