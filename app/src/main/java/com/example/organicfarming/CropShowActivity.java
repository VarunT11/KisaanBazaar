package com.example.organicfarming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CropShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    LinearLayout layout;
    String crop="crop";
    FarmerAdapter farmerAdapter;
    ArrayList<FarmerItem> farmerItems=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_show);
        layout=findViewById(R.id.layout);
        textView=findViewById(R.id.name);



        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            crop = intent.getExtras().getString("crop", "");

            final ImageView img = new ImageView(this);
            Picasso.get().load(intent.getExtras().getString("url", "")).into(img, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    layout.setBackgroundDrawable(img.getDrawable());
                }

                @Override
                public void onError(Exception e) {

                }

            });
        }

        textView.setText(crop);

        recyclerView=findViewById(R.id.recyclerView);
        getAllCrops();

    }

    public void getAllCrops(){

        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Fetching Farmers...");
        progressDialog.show();

        String url = "https://farmerbuyer.herokuapp.com/getfarmerbycrop";

        HashMap<String,String> map = new HashMap<>();
        map.put("name",crop);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        FarmerItem farmerItem=new FarmerItem(jsonObject.getString("name"),0f,0f,jsonObject.getString("uid"),(float) jsonObject.getDouble("rating"),2,10000,jsonObject.getString("address"),"abcd");
                        farmerItems.add(farmerItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                farmerAdapter=new FarmerAdapter(farmerItems);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(CropShowActivity.this));
                recyclerView.setAdapter(farmerAdapter);

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }) {
                @Override
                protected Map<String, String> getParams() {

                    final HashMap<String, String> postParams = new HashMap<String, String>();
                    postParams.put("name", crop);
                    return postParams;
                }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
