package com.example.toyapplication.callback;

import com.example.toyapplication.domain.Product;

import java.util.ArrayList;

public interface ProductsCallBack {
    void onProductsFetchComplete(ArrayList<Product> products);
}
