package com.example.merchandiseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.merchandiseapp.Model.Products;
import com.example.merchandiseapp.ViewHoldler.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminCategoryActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ImageView alcher,tech,research,coding,robo,udgam;
    String category;

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
        setContentView(R.layout.activity_admin_category);
        alcher = findViewById(R.id.alcher);
        tech = findViewById(R.id.techniche);
        research = findViewById(R.id.research);
        coding = findViewById(R.id.coding);
        robo = findViewById(R.id.robotics);
        udgam = findViewById(R.id.udgam);


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


        alcher.setOnClickListener(this);
        tech.setOnClickListener(this);
        research.setOnClickListener(this);
        coding.setOnClickListener(this);
        robo.setOnClickListener(this);
        udgam.setOnClickListener(this);
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
                startActivity(new Intent(AdminCategoryActivity.this, MainActivity.class));
                break;
            case R.id.nav_login:
                Intent intent = new Intent(AdminCategoryActivity.this, loginActivity.class);
                startActivity(intent);
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
        startActivity(new Intent(AdminCategoryActivity.this, MainActivity.class));
    }
}
