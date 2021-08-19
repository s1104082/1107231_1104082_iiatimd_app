package com.example.animalcrossingfront;

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
import com.example.animalcrossingfront.database.UserAuth;
import com.example.animalcrossingfront.database.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentBugs extends Fragment  implements  CritterAdapter.OnNoteListener{
    private String url = "http://10.0.2.2:8000/api/critters";
    private CritterViewModel critterViewModel;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
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

        critterViewModel.getAllBugs().observe(getActivity(), critters -> adapter.setCritter(critters));

        ((CritterAdapter) adapter).addNoteClickListener(this);

       getAllData();

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

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                String token = UserAuth.getInstance(getActivity().getApplicationContext()).getToken();
                Map headers = new HashMap();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 1.0f));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

    }


    @Override
    public void onNoteClick(int critterID) {
        Log.d("TAG", "onNoteClick: pos"+ critterID);
        critterViewModel.updateDonated(critterID);

    }


}
