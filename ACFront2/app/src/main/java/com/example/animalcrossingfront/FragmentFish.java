package com.example.animalcrossingfront;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.animalcrossingfront.CrittersActivities.CritterAdapter;
import com.example.animalcrossingfront.CrittersActivities.CritterViewModel;
import com.example.animalcrossingfront.database.Critters;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentFish extends Fragment {


    private CritterViewModel critterViewModel;
    private LinearLayoutManager linearLayoutManager;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = rootView.findViewById(R.id.RecyclerView);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        final CritterAdapter adapter = new CritterAdapter();
        recyclerView.setAdapter(adapter);

        critterViewModel = new ViewModelProvider(getActivity()).get(CritterViewModel.class);
        critterViewModel.getAllFish().observe(getActivity(), crittersList -> adapter.setCritter(crittersList));

        return rootView;


    }





}
