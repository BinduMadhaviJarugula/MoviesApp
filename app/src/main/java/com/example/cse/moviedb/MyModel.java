package com.example.cse.moviedb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MyModel {
    @PrimaryKey @NonNull
    String id;
    String title;
    String posterpath;
    String backdroppath;
    String overview;
    String releasedate;
    String rating;




    public MyModel(String title, String posterpath, String backdroppath, String overview, String releasedate, String rating, String id) {
        this.title = title;
        this.posterpath = posterpath;
        this.backdroppath = backdroppath;
        this.overview = overview;
        this.releasedate = releasedate;
        this.rating=rating;
        this.id=id;
    }

    public MyModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
