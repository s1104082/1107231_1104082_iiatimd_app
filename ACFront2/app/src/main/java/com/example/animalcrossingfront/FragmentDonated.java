package com.example.animalcrossingfront;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalcrossingfront.CrittersActivities.CritterAdapter;
import com.example.animalcrossingfront.CrittersActivities.CritterViewModel;

public class FragmentDonated extends Fragment implements CritterAdapter.OnNoteListener {

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
        critterViewModel.getAllDonated().observe(getActivity(), crittersList -> adapter.setCritter(crittersList));

        ((CritterAdapter) adapter).addNoteClickListener(this);

        return rootView;


    }


    @Override
    public void onNoteClick(int position, String donated) {
        Log.d("TAG", "onNoteClick: pos"+ position);
        Log.d("clickclick", donated);
        if(donated.equals("Not Donated")){
            critterViewModel.updateDonated(position);
        }

        if(donated.equals("Donated")){
            critterViewModel.updateNotDonated(position);
        }
    }
}
