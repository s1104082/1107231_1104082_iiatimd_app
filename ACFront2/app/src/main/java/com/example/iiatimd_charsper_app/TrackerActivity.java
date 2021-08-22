package com.example.animalcrossingfront;

import android.os.Bundle;

import com.example.animalcrossingfront.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;



public class TrackerActivity extends AppCompatActivity {
//  @Casper for Code Info
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



//
//        //bundle uitlezen
//        Bundle myBundle = getIntent().getExtras();
//        TextView title = (TextView)findViewById(R.id.title);
//        title.setText("Hello, " + myBundle.getString("name"));
    }




}