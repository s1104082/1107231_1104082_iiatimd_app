package com.example.animalcrossingfront.database;

import java.util.List;

public class InsertCrittersTask implements Runnable {

    CrittersAppDatabase db;
    Critters critters;

    public InsertCrittersTask(CrittersAppDatabase db, Critters critters) {
        this.db = db;
        this.critters = critters;
    }

    @Override
    public void run() {db.crittersDAO().insertCritters(critters);    }
}
