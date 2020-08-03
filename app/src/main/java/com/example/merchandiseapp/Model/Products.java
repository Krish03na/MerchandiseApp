package com.example.merchandiseapp.Model;

public class Products {

    private String name, description,price,image,category,pid,date,time;

    public Products() {

    }

    public Products( String category,String date,String description,  String image, String name, String pid, String price,String time ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getPid() {
        return pid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
