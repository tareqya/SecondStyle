package com.example.toyapplication.domain;

import com.example.toyapplication.information.FirebaseId;

import java.util.ArrayList;
import java.util.Date;

public class Order extends FirebaseId {
    private ArrayList<Product> products;
    private Date date;
    private String uid;
    public Order(){}

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Order setProducts(ArrayList<Product> products) {
        this.products = products;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Order setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Order setUid(String uid) {
        this.uid = uid;
        return this;
    }
}
