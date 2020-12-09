package com.example.organicfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

public class BuyerActivity extends AppCompatActivity {

    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarConfiguration appBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        drawerLayout=findViewById(R.id.buyer_drawer);
        navigationView=findViewById(R.id.navigation_buyer);
        collapsingToolbarLayout=findViewById(R.id.buyer_collapsing_toolbar);

        toolbar=findViewById(R.id.toolbar_buyer);
        setSupportActionBar(toolbar);

        appBarConfiguration=new AppBarConfiguration.Builder(
                R.id.buyer_nav_home, R.id.buyer_nav_profile, R.id.buyer_nav_orders, R.id.buyer_nav_favorites)
                .setDrawerLayout(drawerLayout)
                .build();

        navController= Navigation.findNavController(this,R.id.buyer_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.buyer_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}