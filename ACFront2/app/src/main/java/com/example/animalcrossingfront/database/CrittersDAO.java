package com.example.animalcrossingfront.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface CrittersDAO {
    @OnConflictStrategy
    @Query("SELECT * FROM Critters")
    LiveData<List<Critters>> getAllCritters();

    @Query("SELECT * FROM Critters where species = 'Bug'")
    LiveData<List<Critters>> getAllBugs();

    @Query("SELECT * FROM Critters where species = 'Fish'")
    LiveData<List<Critters>> getAllFish();

    @Query("SELECT * FROM Critters where species = 'Sea Creature'")
    LiveData<List<Critters>> getAllSeaCreatures();




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCritters(Critters critters);

    @Update
    void update(Critters critters);
}
