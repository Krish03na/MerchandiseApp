package com.example.merchandiseapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
<<<<<<< HEAD
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  implements  View.OnClickListener {
=======
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
>>>>>>> home page+signin+navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
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

=======
        setContentView(R.layout.home);

        Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_LONG).show();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_draw_open,R.string.nav_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed(){

          if(drawerLayout.isDrawerOpen(GravityCompat.START)){
              drawerLayout.closeDrawer(GravityCompat.START);
          }else super.onBackPressed();
    }

    @Override
    public void onClick(View view){

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this,loginActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
>>>>>>> home page+signin+navigation
}
