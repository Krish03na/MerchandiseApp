package com.example.merchandiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {


     String categoryname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        categoryname = getIntent().getExtras().get("category").toString();

    }
}
