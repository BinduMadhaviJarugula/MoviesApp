package com.example.cse.moviedb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = MyModel.class,version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context){
        if(instance==null){
            instance=Room.databaseBuilder(context,MovieDatabase.class,"roomLiveDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
