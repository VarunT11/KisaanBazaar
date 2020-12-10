package com.example.organicfarming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyerHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuyerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyerHomeFragment newInstance(String param1, String param2) {
        BuyerHomeFragment fragment = new BuyerHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    RecyclerView recyclerView;
    CropListAdapter cropListAdapter;
    ArrayList<CropShowItem> arrayList = new ArrayList<>();
    HashMap<String,Integer> map=new HashMap<String, Integer>();
    HashMap<String,String> urlMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buyer_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        getAllCrops();

        return view;
    }

    public void getAllCrops(){

        ProgressDialog progressDialog =new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Crops...");
        progressDialog.show();

        String url = "https://farmerbuyer.herokuapp.com/getAllCrops";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("response", String.valueOf(response.length()));
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String crop=jsonObject.getString("name");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            int val=map.getOrDefault(crop,0)+1;
                            map.put(crop,val);
                            urlMap.put(crop,jsonObject.getString("image"));
                        }

                        Log.d("size", String.valueOf(map.size()));
                        //jsonResponses.add(email);
                    }

                    for(Map.Entry mapElement : map.entrySet()){
                        CropShowItem cropShowItem = new CropShowItem((String)mapElement.getKey(),urlMap.get((String)mapElement.getKey()),(int)mapElement.getValue(),2);
                        arrayList.add(cropShowItem);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                cropListAdapter = new CropListAdapter(arrayList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(cropListAdapter);

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}