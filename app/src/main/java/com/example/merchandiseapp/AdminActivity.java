package com.example.merchandiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {


     String categoryname;
     EditText product_name,product_description,product_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        categoryname = getIntent().getExtras().get("category").toString();

    }
}
