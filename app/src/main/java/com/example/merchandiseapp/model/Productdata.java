package com.example.merchandiseapp.model;

public class Productdata {
    String name, category,sellerid;
    int price , sizem, sizes,sizexl;

    public Productdata(String name, String category, String sellerid, int price, int sizem, int sizes, int sizexl) {
        this.name = name;
        this.category = category;
        this.sellerid = sellerid;
        this.price = price;
        this.sizem = sizem;
        this.sizes = sizes;
        this.sizexl = sizexl;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSellerid() {
        return sellerid;
    }

    public int getPrice() {
        return price;
    }

    public int getSizem() {
        return sizem;
    }

    public int getSizes() {
        return sizes;
    }

    public int getSizexl() {
        return sizexl;
    }
}
