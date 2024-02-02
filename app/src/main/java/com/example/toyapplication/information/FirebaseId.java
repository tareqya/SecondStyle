package com.example.toyapplication.information;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class FirebaseId implements Serializable {
    private String id;

    public FirebaseId() {
        this.id = "";
    }

    public FirebaseId setId(String id) {
        this.id = id;
        return this;
    }

    @Exclude
    public String getId() {
        return id;
    }
}
