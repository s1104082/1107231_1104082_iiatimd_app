package com.example.animalcrossingfront;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.animalcrossingfront.database.CrittersAppDatabase;
import com.example.animalcrossingfront.database.User;
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentBugs extends Fragment implements  CritterAdapter.OnNoteListener{
    //    @Casper Created fragment with original code
//   05-07-21 @Charlaine changed code at @Casper from array to get from api to room database

    private String url = "http://10.0.2.2:8000/api/critters";
    private CritterViewModel critterViewModel;
    private LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    boolean test = true;


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

        critterViewModel.getAllBugs().observe(getActivity(), critters -> adapter.setCritter(critters));

        ((CritterAdapter) adapter).addNoteClickListener(this);

        if(test){
            getAllData();
            test = false;
        }

        return rootView;

    }

    public void getAllData(){

        final CritterAdapter adapter = new CritterAdapter();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    Critters critter = new Critters();
                    critter.setId(jsonObject.getInt("id"));
                    critter.setName(jsonObject.getString("name"));
                    critter.setSpecies(jsonObject.getString("species"));
                    critter.setImage(jsonObject.getString("critter_image_path"));
                    critter.setLocation(jsonObject.getString("location"));
                    critter.setPrice(jsonObject.getString("price"));
                    critter.setMonth(jsonObject.getString("months_available"));
                    critter.setTime(jsonObject.getString("time_available"));
                    critter.setDonated(jsonObject.getString("donated"));
                    critterViewModel.insert(critter);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            adapter.notifyDataSetChanged();

        }
        , error -> Log.e("Volley", error.toString())) {
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences preferences = getActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE);
                String token = preferences.getString("token","");
                Log.d("authtoken", "getHeaders: " + token);

                params.put("Authorization", token);
                return params;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onNoteClick(int critterID, String donated) {
        Log.d("TAG", "onNoteClick: pos"+ critterID);
        Log.d("clickclick", donated);
        if(donated.equals("Not Donated")){
            critterViewModel.updateDonated(critterID);
        }

        if(donated.equals("Donated")){
            critterViewModel.updateNotDonated(critterID);
        }
    }


}
