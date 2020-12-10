package com.example.organicfarming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    ImageView btnClose;
    BlurView blurView;
    Button btn;
    EditText email,password;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }


        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override public void onStart() {
        super.onStart();


        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.00f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        View view=requireActivity().getLayoutInflater().inflate(R.layout.fragment_login,null);
        blurView=view.findViewById(R.id.blurView);

        float radius = 4f;

        View decorView = getActivity().getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(getContext()))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);


        btnClose=view.findViewById(R.id.btnFragmentClose);
        btn=view.findViewById(R.id.btn);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.this.getDialog().dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                    Toast.makeText(getActivity(),"Please Enter the Required Credentials",Toast.LENGTH_SHORT).show();
                else {
                    sendLoginRequest();
                }
            }
        });



        builder.setView(view);

        return builder.create();

    }

    public void sendLoginRequest(){

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Logging ...");
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, "https://farmerbuyer.herokuapp.com/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("My success",""+response);
                pDialog.dismiss();
                String type="", uid="";

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    type=jsonObject.getString("type");
                    uid=jsonObject.getString("uid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(type.equalsIgnoreCase("0")) {
                    Toast.makeText(getContext(), "Buyer Login Successful", Toast.LENGTH_SHORT).show();
                    updateCurrentUser(uid,"buyer");
                    startActivity(new Intent(getActivity(),BuyerActivity.class));
                    getActivity().finish();
                }
                else if (type.equalsIgnoreCase("1")){
                    Toast.makeText(getContext(),"Farmer Login Successful",Toast.LENGTH_SHORT).show();
                    updateCurrentUser(uid,"farmer");
                    startActivity(new Intent(getActivity(),FarmerActivity.class));
                    getActivity().finish();
                }
                else{
                    //credential not match
                    Toast.makeText(getContext(),"Login Failed",Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                final HashMap<String, String> postParams = new HashMap<String, String>();
                postParams.put("email", email.getText().toString());
                postParams.put("password", password.getText().toString());
                return postParams;
            }
        };
        queue.add(request);
    }

    private void updateCurrentUser(String uid,String type){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("logged",true);
        editor.putString("UID",uid);
        editor.putString("type",type);
        editor.apply();

        Log.d("USER",uid+" "+type);

        Log.d("updated",sharedPreferences.getString("uid","12"));
    }

}