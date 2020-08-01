package com.example.merchandiseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements  View.OnClickListener {

    Button register,backtologin;
    TextInputLayout regusername,regfullname,regemail,regphone,regpwd,regcnf;

    FirebaseDatabase root;
    DatabaseReference ref;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.register);
        backtologin = (Button) findViewById(R.id.backtologin);

        regusername =  (TextInputLayout) findViewById(R.id.username);
        regfullname = (TextInputLayout) findViewById(R.id.name);
        regemail = (TextInputLayout) findViewById(R.id.email);
        regphone = (TextInputLayout) findViewById(R.id.number);
        regpwd = (TextInputLayout) findViewById(R.id.password);
        regcnf = (TextInputLayout) findViewById(R.id.cnfm_password);


        register.setOnClickListener(this);
        backtologin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        regemail.setError(null);
        switch(view.getId()){
            case R.id.backtologin :
                Intent intent =new Intent(SignUpActivity.this, loginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.register:
                registeruser(view);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(SignUpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private Boolean validname(View view){
        String name = regfullname.getEditText().getText().toString();

        if(name.isEmpty()){
            regfullname.setError("Field cannot be empty");
            return false;
        }else{
            regfullname.setError(null);
            return true;
        }
    }

    private Boolean validuser(View view){
        String username = regusername.getEditText().getText().toString();
        String regex = "^[a-z0-9_-]{3,15}$";

        if(username.isEmpty()){
            regusername.setError("Field cannot be empty");
            return false;
        }else if(username.matches(regex)){
            regusername.setError(null);

            return true;
        }else{
            regusername.setError("Username should be min 3 and max 15 with no whitespaces");
            return false;
        }
    }

    private Boolean validemail(View view){
        String email = regemail.getEditText().getText().toString();
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.isEmpty()){
            regemail.setError("Field cannot be empty");
            return false;
        }else if(!email.matches(regex)){
            regemail.setError("Invalid email address");
            return false;
        }
        else{
            regemail.setError(null);
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validphone(View view){
        String phone = regphone.getEditText().getText().toString();

        if(phone.isEmpty()){
            regphone.setError("Field cannot be empty");
            return false;
        }else{
            regphone.setError(null);
            regphone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validpass(View view){
        String password = regpwd.getEditText().getText().toString();
        String pwd = regcnf.getEditText().getText().toString();

        if(password.isEmpty() || pwd.isEmpty()){
            if(password.isEmpty()){
                regpwd.setError("Field cannot be empty");
                return false;
            }
            if(pwd.isEmpty()){
                regcnf.setError("Field cannot be empty");
                return false;
            }
        }else if(password.equals(pwd)){
            String val = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

            if(password.matches(val)){
                regpwd.setError(null);
                regpwd.setErrorEnabled(false);
                regcnf.setError(null);
                regcnf.setErrorEnabled(false);
                return true;
            }else{
                 regpwd.setError("length 8-40 should contain 1 digit,lowercase,uppercase and a special character");
                return false;
            }
        }else{
            regcnf.setError("Passwords do not match");
            return false;
        }

        return null;
    }


    private void registeruser(View view){
        root = FirebaseDatabase.getInstance();
        ref = root.getReference("newusers");
        final String name = regfullname.getEditText().getText().toString();
        final String username = regusername.getEditText().getText().toString();
        final String email = regemail.getEditText().getText().toString();
        final String phone = regphone.getEditText().getText().toString();
        final String password = regpwd.getEditText().getText().toString();


        if(validname(view) && validuser(view) && validemail(view) && validpass(view) && validphone(view)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                //stores user information in realtime database
                                UserHelper helper = new UserHelper(username, name, phone, email);
                                //the below statement does not support email as child
                                ref.child(username).setValue(helper);
                                //The below commented lines verify the email adddress given by the user
                                AuthResult result = task.getResult();
                                FirebaseUser user =result.getUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                                user.updateProfile(profileUpdates);

//                                user.sendEmailVerification();

                                Intent intent = new Intent(SignUpActivity.this, loginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    regemail.setError("Email already taken");
                                    regemail.requestFocus();
                                }
                            }
                        }
                    });

        }else{
            return;
        }


    }



}
