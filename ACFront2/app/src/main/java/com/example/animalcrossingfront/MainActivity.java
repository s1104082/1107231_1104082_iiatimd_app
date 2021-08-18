package com.example.animalcrossingfront;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //button to go to second screen
//        Button toSecondScreenButton = findViewById(R.id.buttonLogin);
//        toSecondScreenButton.setOnClickListener((OnClickListener) this);

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


