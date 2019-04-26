package com.example.cse.moviedb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    public RepositoryMovie repositoryMovie;
    String idread;

    public LiveData<List<MyModel>> getAllFavData;

    public MovieViewModel(@NonNull Application application) {

        super(application);
        repositoryMovie=new RepositoryMovie(application);

        getAllFavData=repositoryMovie.getGetAllData();
    }
    public  void insertData(MyModel contact){
        repositoryMovie.insert(contact);
    }
    public  void deleteData(MyModel contact){
        repositoryMovie.delete(contact);
    }

    public  LiveData<List<MyModel>> getGetAllFavData(){
        return getAllFavData;
    }

    public String getIdread(String id){
        idread=repositoryMovie.read(id);
        return idread;
    }

}
