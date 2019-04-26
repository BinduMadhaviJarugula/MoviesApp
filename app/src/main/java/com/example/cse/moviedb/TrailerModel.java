package com.example.cse.moviedb;

public class TrailerModel {
    String mkey;
    String mname;
    public TrailerModel(String mkey, String mname) {
        this.mkey = mkey;
        this.mname = mname;
    }


    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }


}
