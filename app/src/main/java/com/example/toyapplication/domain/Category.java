package com.example.toyapplication.domain;

public class Category {
    private String name;
    private int image;
    private boolean selected;
    public Category(){
        this.selected = false;
    }
    public Category(String name, int image){
        this.image = image;
        this.name = name;
        this.selected = false;
    }
    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public int getImage() {
        return image;
    }

    public Category setImage(int image) {
        this.image = image;
        return this;
    }

    public boolean isSelected(){
        return selected;
    }

    public Category setSelected(boolean select){
        this.selected = select;
        return this;
    }
}
