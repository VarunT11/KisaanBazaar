package com.example.organicfarming;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class RegisterFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
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
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
    Button btn_confirm,btn_farmer,btn_buyer;
    LinearLayout layout1, layout2;
    EditText email, password,location,contact;
    AlertDialog.Builder builder;
    int UserType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
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

        View view = requireActivity().getLayoutInflater().inflate(R.layout.fragment_register, null);
        blurView =view.findViewById(R.id.blurView);
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

        btnClose = view.findViewById(R.id.btnFragmentClose);
        btn_buyer=view.findViewById(R.id.buyer);
        btn_farmer=view.findViewById(R.id.farmer);
        btn_confirm=view.findViewById(R.id.btn_confirm);
        contact=view.findViewById(R.id.contact);
        location=view.findViewById(R.id.address);
        layout1=view.findViewById(R.id.first);
        layout2=view.findViewById(R.id.second);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment.this.getDialog().dismiss();
            }
        });

        btn_farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserType=0;
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        });

        btn_buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserType=1;
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserType==1)
                    startActivity(new Intent(getActivity(),BuyerActivity.class));
                else
                    startActivity(new Intent(getActivity(),FarmerActivity.class));
                getActivity().finish();
            }
        });


        builder.setView(view);

        return builder.create();

    }

    public void sendLoginRequest(){

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Registration ...");
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, "https://farmerbuyer.herokuapp.com/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("My success",""+response);
                pDialog.dismiss();
                if(response.equalsIgnoreCase("true")) {

                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }else
                {
                    //credential not match
                    Toast.makeText(getContext(),"Login Failed",Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "my error :"+error, Toast.LENGTH_LONG).show();
                Log.i("My error",""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                final HashMap<String, String> postParams = new HashMap<String, String>();
                postParams.put("email", email.getText().toString());
                postParams.put("contact",contact.getText().toString());
                postParams.put("password", password.getText().toString());
                return postParams;
            }
        };
        queue.add(request);
    }

}
