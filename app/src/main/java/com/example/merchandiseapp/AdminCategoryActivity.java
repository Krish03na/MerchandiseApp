package com.example.merchandiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView alcher,tech,research,coding,robo,udgam;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        alcher = (ImageView) findViewById(R.id.alcher);
        tech = (ImageView) findViewById(R.id.techniche);
        research = (ImageView) findViewById(R.id.research);
        coding =(ImageView) findViewById(R.id.coding);
        robo = (ImageView) findViewById(R.id.robotics);
        udgam =(ImageView) findViewById(R.id.udgam);


        alcher.setOnClickListener(this);
        tech.setOnClickListener(this);
        research.setOnClickListener(this);
        coding.setOnClickListener(this);
        robo.setOnClickListener(this);
        udgam.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.alcher:
                category = "alcher";
                break;
            case R.id.techniche:
                category = "techniche";
                break;
            case R.id.research:
                category = "research";
                break;
            case R.id.coding:
                category = "coding";
                break;
            case R.id.robotics:
                category = "robotics";
                break;
            case R.id.udgam:
                category = "udgam";
                break;
        }
        Intent intent = new Intent(AdminCategoryActivity.this, AdminActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
