package com.example.organicfarming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    LoginFragment loginFragment=new LoginFragment();
    RegisterFragment registerFragment = new RegisterFragment();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.show(getSupportFragmentManager(),"Login Dialog");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment.show(getSupportFragmentManager(),"Register Dialog");
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}