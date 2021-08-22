package com.example.animalcrossingfront.database;
//@Charlaine For Info
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

    @Query("SELECT * FROM Critters where species = 'Bug' and donated = 'Not Donated'")
    LiveData<List<Critters>> getAllBugs();

    @Query("SELECT * FROM Critters where species = 'Fish' and donated = 'Not Donated'")
    LiveData<List<Critters>> getAllFish();

    @Query("SELECT * FROM Critters where species = 'Sea Creature' and donated = 'Not Donated'")
    LiveData<List<Critters>> getAllSeaCreatures();

    @Query("SELECT * FROM Critters where donated = 'Donated'")
    LiveData<List<Critters>> getAllDonated();

    @Query("UPDATE Critters set donated = 'Donated' where id = :id")
    void updateDonated(int id);

    @Query("UPDATE Critters set donated = 'Not Donated' where id = :id")
    void updateNotDonated(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCritters(Critters critters);

    @Update
    void update(Critters critters);
}
