package com.example.organicfarming;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CropShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FarmerAdapter farmerAdapter;
    ArrayList<FarmerItem> farmerItems=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_show);

        farmerItems.add(new FarmerItem("Farmer1",12,12,"123",3f,3,12000,"abcd","abcd"));
        farmerItems.add(new FarmerItem("Farmer2",12,12,"123",4f,3,12000,"abcd","abcd"));
        farmerItems.add(new FarmerItem("Farmer3",12,12,"123",5f,3,12000,"abcd","abcd"));
        farmerItems.add(new FarmerItem("Farmer4",12,12,"123",2f,3,12000,"abcd","abcd"));

        recyclerView=findViewById(R.id.recyclerView);
        farmerAdapter=new FarmerAdapter(farmerItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(farmerAdapter);
    }
}
