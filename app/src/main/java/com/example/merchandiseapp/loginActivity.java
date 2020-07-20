package com.example.merchandiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class loginActivity extends AppCompatActivity {


    Button callsignup, login_button;
    TextInputLayout user,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         callsignup = (Button) findViewById(R.id.signup);
         login_button = (Button) findViewById(R.id.login_btn);
         user = (TextInputLayout) findViewById(R.id.username);
         pwd = (TextInputLayout) findViewById(R.id.password);



        callsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
