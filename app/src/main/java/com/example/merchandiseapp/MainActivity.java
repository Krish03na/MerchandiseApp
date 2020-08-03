package com.example.merchandiseapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu mymenu;
    TextView hname;
    MenuItem login,logout,profile;
    View hview;
    CircleImageView profimage;
    FirebaseAuth mAuth;
    FirebaseDatabase root;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //      setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_draw_open, R.string.nav_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

       mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        Menu menu = navigationView.getMenu();
        hview = navigationView.getHeaderView(0);
        hname = hview.findViewById(R.id.headername);
        profimage = hview.findViewById(R.id.profile_image);

        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem profile = menu.findItem(R.id.nav_profile);
        MenuItem login = menu.findItem(R.id.nav_login);

        root = FirebaseDatabase.getInstance();
        ref = root.getReference("newusers");

        String email ="Welcome";
        user = mAuth.getCurrentUser();

        if(user!=null) {
            email = user.getEmail();
            menu.removeItem(R.id.nav_login);
       }else {
            menu.removeItem(R.id.nav_logout);
            menu.removeItem(R.id.nav_profile);
        }

        hname.setText(email);
        navigationView.invalidate();
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mymenu = menu;
        login =  mymenu.findItem(R.id.nav_login);
        logout = mymenu.findItem(R.id.nav_logout);
        profile = mymenu.findItem(R.id.nav_profile);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this, loginActivity.class));
                break;
            case R.id.nav_logout:
                signOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

}
