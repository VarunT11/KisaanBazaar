package com.example.organicfarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

public class BuyerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.buyer_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_buyer_home: {
                navController.navigate(R.id.buyer_nav_home);
                break;
            }

            case R.id.nav_buyer_profile: {
                navController.navigate(R.id.buyer_nav_profile);
                break;
            }

            case R.id.nav_buyer_my_orders: {
                navController.navigate(R.id.buyer_nav_orders);
                break;
            }

            case R.id.nav_buyer_favorites:{
                navController.navigate(R.id.buyer_nav_favorites);
                break;
            }

            case R.id.nav_buyer_log_out:{
                SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("logged",false);
                editor.apply();
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            }

        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}