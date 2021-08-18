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
    private CritterRepository critterRepository;

    public CritterViewModel(@NonNull Application application) {
        super(application);
        critterRepository = new CritterRepository(application);
        allCritters = critterRepository.getAllCritters();
        allBugs = critterRepository.getAllBugs();
        allFish = critterRepository.getAllFish();
        allSeaCreatures = critterRepository.getAllSeaCreatures();
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



    public void insert(Critters critters) { critterRepository.insert(critters);};

    public void updateDonated(Critters critters){
        critterRepository.update(critters);

    }


}
