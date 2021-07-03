package com.example.animalcrossingfront;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button toLoginButton = findViewById(R.id.loginButtonStart);
        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginScreen();
            }
        });

        final Button toRegisterButton = findViewById(R.id.registerButtonStart);
        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterScreen();
            }
        });



    }

    public void toLoginScreen(){
        Intent loginStartIntent = new Intent(this, LoginActivity.class);
        startActivity(loginStartIntent);

    }

    public void toRegisterScreen(){
        Intent registerStartIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerStartIntent);

    }
}
