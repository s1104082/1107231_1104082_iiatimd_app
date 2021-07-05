package com.example.iiatimd_charsper_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button to go to second screen
        Button toSecondScreenButton = findViewById(R.id.buttonLogin);
        toSecondScreenButton.setOnClickListener(this);

    }
    public void onClick(View v){
        //to give data to other screen

        Bundle bundleForSecondScreen = new Bundle();
        String name = "User";
        bundleForSecondScreen.putString("name", name);


        Intent toNextScreenIntent = new Intent(this, TrackerActivity.class);
        toNextScreenIntent.putExtras(bundleForSecondScreen);
        startActivity(toNextScreenIntent);
    }
}
