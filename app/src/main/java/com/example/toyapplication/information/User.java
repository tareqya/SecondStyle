package com.example.toyapplication.information;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User extends FirebaseId implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String imagePath;
    private String imageUrl;

    public User() {}

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public User setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    @Exclude
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
}
