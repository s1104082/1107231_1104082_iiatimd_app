package com.example.animalcrossingfront.CrittersActivities;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.animalcrossingfront.database.Critters;
import com.example.animalcrossingfront.database.CrittersAppDatabase;
import com.example.animalcrossingfront.database.CrittersDAO;

import java.util.List;

public class CritterRepository {
    private CrittersDAO crittersDAO;
    private LiveData<List<Critters>> allCritters;
    private LiveData<List<Critters>> allBugs;
    private LiveData<List<Critters>> allFish;
    private LiveData<List<Critters>> allSeaCreatures;

    public CritterRepository(Application application){
        CrittersAppDatabase database = CrittersAppDatabase.getInstance(application);
        crittersDAO = database.crittersDAO();
        allCritters = crittersDAO.getAllCritters();
        allBugs = crittersDAO.getAllBugs();
        allFish = crittersDAO.getAllFish();
        allSeaCreatures = crittersDAO.getAllSeaCreatures();
    }

    public LiveData<List<Critters>> getAllCritters(){ return allCritters; }

    public LiveData<List<Critters>> getAllBugs(){ return allBugs; }
    public LiveData<List<Critters>> getAllFish(){ return allFish; }

    public LiveData<List<Critters>> getAllSeaCreatures(){ return allSeaCreatures; }

    public void insert(Critters critters){
//            CrittersAppDatabase.databaseWriteExecutor.execute(() -> {crittersDAO.insertCritters(critters);});
        new InsertCritterAsyncTask(crittersDAO).execute(critters);
    }

    public void update(Critters critters){
        crittersDAO.update(critters);

    }



    private static class InsertCritterAsyncTask extends AsyncTask<Critters, Void, Void> {

        private CrittersDAO crittersDOA;

        private InsertCritterAsyncTask(CrittersDAO crittersDAO){
            this.crittersDOA = crittersDAO;
        }

        @Override
        protected Void doInBackground(Critters... critters) {
            crittersDOA.insertCritters(critters[0]);
            return null;
        }
    }

    private static class UpdateCritterAsyncTask extends AsyncTask<Critters, Void, Void> {

        private CrittersDAO crittersDOA;

        private UpdateCritterAsyncTask(CrittersDAO crittersDAO){
            this.crittersDOA = crittersDAO;
        }

        @Override
        protected Void doInBackground(Critters... critters) {
            crittersDOA.insertCritters(critters[0]);
            return null;
        }
    }

}
