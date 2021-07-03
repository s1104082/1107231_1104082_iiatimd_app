package com.example.animalcrossingfront.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CrittersDAO {
    @OnConflictStrategy
    @Query("SELECT * FROM Critters")
    LiveData<List<Critters>> getAllFood();

}
