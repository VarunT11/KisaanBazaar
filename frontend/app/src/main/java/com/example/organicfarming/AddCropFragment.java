package com.example.organicfarming;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCropFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCropFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCropFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCropFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCropFragment newInstance(String param1, String param2) {
        AddCropFragment fragment = new AddCropFragment();
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

    Spinner cropName;
    String uid;
    EditText etQuantity, etPricePerTon, etTotalPrice;
    SeekBar seekHarvestTime;
    Button btnSubmit;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uid=getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("UID",null);
        cropName=view.findViewById(R.id.spinner);
        etQuantity=view.findViewById(R.id.etCropQuantity);
        etPricePerTon=view.findViewById(R.id.etCropCost);
        etTotalPrice=view.findViewById(R.id.etCropTotalCost);
        seekHarvestTime=view.findViewById(R.id.seekHarvestTime);
        etTotalPrice.setEnabled(false);

        btnSubmit=view.findViewById(R.id.btnSubmitCrop);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etQuantity.getText().toString().isEmpty() || etPricePerTon.getText().toString().isEmpty())
                    Toast.makeText(getActivity(),"Please Fill all the Details",Toast.LENGTH_SHORT).show();
                else {
                    addCrop();
//                    etTotalPrice.setText(Integer.parseInt(etQuantity.getText().toString())*Integer.parseInt(etPricePerTon.getText().toString()));
                }
            }
        });
    }

    private void addCrop(){
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Adding Crop");
        progressDialog.show();

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        StringRequest request=new StringRequest(Request.Method.POST, "https://farmerbuyer.herokuapp.com/addCrop", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Crop Added Successfully",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ERROR",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                final HashMap<String, String> postParams = new HashMap<String, String>();
                postParams.put("name",cropName.getSelectedItem().toString());
                postParams.put("image","image");
                postParams.put("farmeruid",uid);
                postParams.put("pricePerTon",etPricePerTon.getText().toString());
                postParams.put("timeleft",Integer.toString(seekHarvestTime.getProgress()*7));
                postParams.put("latitude","0");
                postParams.put("longitude","0");
                postParams.put("weight",etQuantity.getText().toString());
                return postParams;
            }
        };
        queue.add(request);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_crop, container, false);
    }
}