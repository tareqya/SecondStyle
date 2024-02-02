package com.example.toyapplication.Domain;

import com.example.toyapplication.information.FirebaseId;

import java.util.ArrayList;
import java.util.Date;

public class Order extends FirebaseId {
    private ArrayList<Product> products;
    private Date date;

    public Order(){}


}
