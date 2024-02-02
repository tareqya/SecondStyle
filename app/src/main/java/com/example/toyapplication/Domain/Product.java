package com.example.toyapplication.Domain;

import com.example.toyapplication.information.FirebaseId;
import com.google.firebase.firestore.Exclude;

public class Product extends FirebaseId {
    private String size;
    private String imagePath;
    private String imageUrl;
    private double price;

    public Product() {}

    public String getSize() {
        return size;
    }

    public Product setSize(String size) {
        this.size = size;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Product setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public Product setImageUrl(String imageUrl){
        this.imagePath = imageUrl;
        return this;
    }

    @Exclude
    public String getImageUrl(){
        return imageUrl;
    }
}
