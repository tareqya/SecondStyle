package com.example.toyapplication.domain;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    public Cart(){
        products = new ArrayList<>();
    }
    public double totalPrice(){
        double sum = 0;
        for(int i = 0; i < products.size(); i++){
            sum += products.get(i).getPrice();
        }

        return sum;
    }

    public ArrayList<Product> getProducts(){
        return this.products;
    }

    public boolean addProduct(Product product){
        for(Product p: this.products){
            if(p.getId().equals(p.getId()))
                return false;
        }

        this.products.add(product);
        return true;
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }
}
