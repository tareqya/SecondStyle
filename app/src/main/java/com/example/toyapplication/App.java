package com.example.toyapplication;

import android.app.Application;

import com.example.toyapplication.domain.Cart;

public class App  extends Application {
    public static Cart cart = new Cart();

}
