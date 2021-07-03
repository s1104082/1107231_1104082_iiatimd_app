package com.example.animalcrossingfront.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Critters.class, }, version = 1)
public abstract class CrittersAppDatabase extends RoomDatabase {

    private static CrittersAppDatabase instance;
    public abstract CrittersDAO crittersDAO();

    public static synchronized CrittersAppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static CrittersAppDatabase create(final Context context){
        return  Room.databaseBuilder(context, CrittersAppDatabase.class, "Critters").fallbackToDestructiveMigration().build();

    }


}
