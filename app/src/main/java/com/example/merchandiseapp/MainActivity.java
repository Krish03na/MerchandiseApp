package com.example.merchandiseapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_LONG).show();


        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }


    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.button4:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.button3:
                Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
