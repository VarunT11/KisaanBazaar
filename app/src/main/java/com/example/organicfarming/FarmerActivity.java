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

public class FarmerActivity extends AppCompatActivity {

    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarConfiguration appBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);

        drawerLayout=findViewById(R.id.farmer_drawer);
        navigationView=findViewById(R.id.navigation_farmer);
        collapsingToolbarLayout=findViewById(R.id.farmer_collapsing_toolbar);

        toolbar=findViewById(R.id.toolbar_farmer);
        setSupportActionBar(toolbar);

        appBarConfiguration=new AppBarConfiguration.Builder(
                R.id.farmer_nav_home, R.id.farmer_nav_profile, R.id.farmer_nav_orders)
                .setDrawerLayout(drawerLayout)
                .build();

        navController= Navigation.findNavController(this,R.id.farmer_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.farmer_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}