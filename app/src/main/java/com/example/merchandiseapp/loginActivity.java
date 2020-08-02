package com.example.merchandiseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {


    Button callsignup, login_button, forget_password;
    TextInputLayout user, pwd;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView adminb, nadmin;
    ProgressDialog pd;
    public boolean isadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isadmin =false;
        callsignup = (Button) findViewById(R.id.signup);
        login_button = (Button) findViewById(R.id.login_btn);
        forget_password = (Button) findViewById(R.id.frg_pwd);
        user = (TextInputLayout) findViewById(R.id.username);
        pwd = (TextInputLayout) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        adminb = (TextView) findViewById(R.id.adminb);
        nadmin = (TextView) findViewById(R.id.nadminb) ;

      callsignup.setOnClickListener(this);
      login_button.setOnClickListener(this);
      forget_password.setOnClickListener(this);
      adminb.setOnClickListener(this);
      nadmin.setOnClickListener(this);
      mAuth = FirebaseAuth.getInstance();

      pd = new ProgressDialog(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        nadmin.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.login_btn :
                login(view);
                break;
            case R.id.signup:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.adminb:
                isadmin = true;
                login_button.setText("Login Admin");
                adminb.setVisibility(View.INVISIBLE);
                nadmin.setVisibility(View.VISIBLE);
                break;
            case R.id.nadminb:
                isadmin = false;
                login_button.setText("Login");
                adminb.setVisibility(View.VISIBLE);
                nadmin.setVisibility(View.INVISIBLE);
                break;
            case R.id.frg_pwd:
                showRecoverPasswordDialog();

        }
    }

    private void showRecoverPasswordDialog() {

        //Alert Dailog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);

        final EditText emailID = new EditText(this);
        emailID.setHint("Email");
        emailID.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailID.setMinEms(16);
        linearLayout.addView(emailID);
        linearLayout.setPadding(50,10,10,10);

        builder.setView(linearLayout);
        //recover button
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                   String email = emailID.getText().toString().trim();
                   beginRecoverymail(email);

            }
        });
        //cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //dismiss dailog
                dialog.dismiss();
            }
        });

        //show dailog box
        builder.create().show();
    }

    private void beginRecoverymail(String email) {
        pd.setMessage("Sending email...");
        pd.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
               Toast.makeText(getApplicationContext(), "Error :" + e , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validuser(View view) {
        String username = user.getEditText().getText().toString();

        if (username.isEmpty()) {
            user.setError("Field cannot be empty");
            return false;
        } else {
            user.setError(null);
            user.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validpass(View view) {

        String pass = pwd.getEditText().getText().toString();

        if (pass.isEmpty()) {
            pwd.setError("Field cannnot be empty");
            return false;
        } else {
            pwd.setError(null);
            pwd.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        String username = user.getEditText().getText().toString();
        String password = pwd.getEditText().getText().toString();

        if (!validuser(view) || !validpass(view)) {
            return;
        } else {
                isUser();
        }

    }

    private void isUser() {
        final String enteredemail = user.getEditText().getText().toString().trim();
        final String enteredPassword = pwd.getEditText().getText().toString().trim();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mAuth.signInWithEmailAndPassword(enteredemail, enteredPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        if (task.isSuccessful()) {
                            Intent intent;
                             if(isadmin){
                                 intent = new Intent(loginActivity.this,AdminCategoryActivity.class);
                             }else {
                                 intent = new Intent(loginActivity.this, MainActivity.class);
                             }
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                          Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
