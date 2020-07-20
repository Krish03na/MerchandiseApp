package com.example.merchandiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button register,backtologin;
    TextInputLayout regusername,regfullname,regemail,regphone,regpwd,regcnf;

    FirebaseDatabase root;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        register = (Button) findViewById(R.id.register);
        backtologin = (Button) findViewById(R.id.backtologin);
        regusername =  (TextInputLayout) findViewById(R.id.username);
        regfullname = (TextInputLayout) findViewById(R.id.name);
        regemail = (TextInputLayout) findViewById(R.id.email);
        regphone = (TextInputLayout) findViewById(R.id.number);
        regpwd = (TextInputLayout) findViewById(R.id.password);
        regcnf = (TextInputLayout) findViewById(R.id.cnfm_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     root = FirebaseDatabase.getInstance();
                     ref = root.getReference("user");

                   String name = regfullname.getEditText().getText().toString();
                   String username = regusername.getEditText().getText().toString();
                   String email = regemail.getEditText().getText().toString();
                   String phone = regphone.getEditText().getText().toString();
                   String password = regpwd.getEditText().getText().toString();
                   String pwd = regcnf.getEditText().getText().toString();

                   if(password.equals(pwd) == true)
                   {
                       UserHelper helper = new UserHelper(username,name,email,phone,password);
                       ref.child(phone).setValue(helper);
                   }else{
                      regcnf.setError(getString(R.string.do_not_match));
                   }
            }
        });
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, loginActivity.class));
            }
        });
    }
}
