package com.example.iiatimd_charsper_app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentFish extends Fragment implements FragmentAdapter.OnNoteListener {

    Context thiscontext;

    RecyclerView recyclerView;

    String s1[], s2[], s3[], s4[], s5[];
    int fishimages[] = {R.drawable.tarantula, R.drawable.ladybug, R.drawable.scorpion, R.drawable.wasp};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //array invullen
        s1 = getResources().getStringArray(R.array.fish_name_array);
        s2 = getResources().getStringArray(R.array.fish_location_array);
        s3 = getResources().getStringArray(R.array.fish_month_array);
        s4 = getResources().getStringArray(R.array.fish_time_array);
        s5 = getResources().getStringArray(R.array.fish_price_array);

        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = rootView.findViewById(R.id.RecyclerView);

        thiscontext = container.getContext();
        FragmentAdapter myAdapter = new FragmentAdapter(thiscontext, s1, s2, s3, s4, s5, fishimages, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(thiscontext));


        return rootView;


    }

    @Override
    public void onNoteClick(int position) {
        Log.d("TAG", "onNoteClick: pos"+ position);

        ImageView imageView = (ImageView) getActivity().findViewById(R.id.ImageView);
        if (imageView.getVisibility() == View.VISIBLE){
            imageView.setVisibility(View.INVISIBLE);
        } else{
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
