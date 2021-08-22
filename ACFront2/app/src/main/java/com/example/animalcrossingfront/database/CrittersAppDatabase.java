package com.example.animalcrossingfront.database;


import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Critters.class, }, version = 6, exportSchema = false)
public abstract class CrittersAppDatabase extends RoomDatabase {

    public abstract CrittersDAO crittersDAO();
    private static volatile CrittersAppDatabase instance;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);




    public static synchronized CrittersAppDatabase getInstance(final Context context){
        if(instance == null){
            synchronized (CrittersAppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            CrittersAppDatabase.class, "crittersDB")
                            .enableMultiInstanceInvalidation()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }



}
