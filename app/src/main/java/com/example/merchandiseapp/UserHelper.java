package com.example.merchandiseapp;

public class UserHelper {

    String username,name,email,phone,password;

    public UserHelper() {
       //empty constructor
    }

    public UserHelper(String username, String name, String email, String phone, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
