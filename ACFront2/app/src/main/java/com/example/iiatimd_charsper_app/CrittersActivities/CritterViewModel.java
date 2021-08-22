package com.example.animalcrossingfront.CrittersActivities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animalcrossingfront.database.Critters;

import java.util.List;

public class CritterViewModel extends AndroidViewModel {

    private LiveData<List<Critters>> allCritters;
    private LiveData<List<Critters>> allBugs;
    private LiveData<List<Critters>> allFish;
    private LiveData<List<Critters>> allSeaCreatures;
    private LiveData<List<Critters>> allDonated;

    private CritterRepository critterRepository;

    public CritterViewModel(@NonNull Application application) {
        super(application);
        critterRepository = new CritterRepository(application);
        allCritters = critterRepository.getAllCritters();
        allBugs = critterRepository.getAllBugs();
        allFish = critterRepository.getAllFish();
        allSeaCreatures = critterRepository.getAllSeaCreatures();
        allDonated= critterRepository.getAllDonated();
    }
    public LiveData<List<Critters>> getAllCritters(){
        return allCritters;
    }
    public LiveData<List<Critters>> getAllBugs(){
        return allBugs;
    }
    public LiveData<List<Critters>> getAllFish(){
        return allFish;
    }

    public LiveData<List<Critters>> getAllSeaCreatures(){
        return allSeaCreatures;
    }
    public LiveData<List<Critters>> getAllDonated(){ return allDonated; }

    public void updateDonated(int crittersID){
        critterRepository.updateDonated(crittersID);

    }

    public void updateNotDonated(int crittersID){
        critterRepository.updateNotDonated(crittersID);

    }


    public void insert(Critters critters) { critterRepository.insert(critters);};




}
