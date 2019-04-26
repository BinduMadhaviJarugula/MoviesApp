package com.example.cse.moviedb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    public void insertfav(MyModel myModel);

    @Query("select * from MyModel")
    public LiveData<List<MyModel>> getfav();

    @Delete
    public void deletefav(MyModel myModel);

    @Query("select id from MyModel where id=:readid")
    public String read(String readid);



}
