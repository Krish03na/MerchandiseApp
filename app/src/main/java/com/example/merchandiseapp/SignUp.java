package com.example.merchandiseapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText user,pass;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

      user = (EditText) findViewById(R.id.username);
      pass = (EditText) findViewById(R.id.password);



      mAuth = FirebaseAuth.getInstance();

      findViewById(R.id.regbutton).setOnClickListener(this);
      findViewById(R.id.signbutton).setOnClickListener(this);
    }

    private  void registerUser(){
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (email.isEmpty()) {
            user.setError("Email is required");
            user.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user.setError("Please enter a valid email");
            user.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            pass.setError("Minimum lenght of password should be 6");
            pass.requestFocus();
            return;
        }

        
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"user registered",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(getApplicationContext(), "Failed Registration: "+e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.regbutton:
                registerUser();
                break;

            case R.id.signbutton:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
