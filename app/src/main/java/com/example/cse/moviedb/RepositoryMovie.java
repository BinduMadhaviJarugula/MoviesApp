package com.example.cse.moviedb;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class RepositoryMovie {
    public static MovieDao movieDao;
    LiveData<List<MyModel>> getAllData;

    public RepositoryMovie(Context context) {

        MovieDatabase contactDatabase = MovieDatabase.getInstance(context);
        movieDao = contactDatabase.movieDao();
        getAllData = movieDao.getfav();
    }

    LiveData<List<MyModel>> getGetAllData() {
        return getAllData;
    }


    public void insert(MyModel contact) {
        new InsertTask().execute(contact);
    }



    public void delete(MyModel contact) {
        new DeleteTask().execute(contact);
    }


    public class InsertTask extends AsyncTask<MyModel, Void, Void> {

        @Override
        protected Void doInBackground(MyModel... contacts) {
            movieDao.insertfav(contacts[0]);
            return null;
        }
    }

    public class DeleteTask extends AsyncTask<MyModel, Void, Void> {


        @Override
        protected Void doInBackground(MyModel... myModels) {
            movieDao.deletefav(myModels[0]);
            return null;
        }
    }
    String read(String id){
        return movieDao.read(id);
    }

}
