package com.example.merchandiseapp;

public class UserHelper {

    String username, name, phone, email;
    public UserHelper() {

    }
    public UserHelper(String username, String name, String phone, String email) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.email = email;

    }
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }


}
