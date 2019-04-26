package com.example.cse.moviedb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieActivity extends AppCompatActivity {


    ImageView poster,backdrop;
    TextView tit,overview,reldate,tvrating;
    String rev,trailer;
    ArrayList<ModelClass> arrayList;
    RecyclerView recyclerView,trecyclerView;
    ArrayList<TrailerModel> arrayTrailer;
    MaterialFavoriteButton materialFavoriteButton;

    String favtit;
    String favback;
    String favpost;
    String favdes;
    String favrel;
    String favrating;
    String favmovieid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        poster=findViewById(R.id.aposter);
        backdrop=findViewById(R.id.abackpath);
        tit=findViewById(R.id.atitle);
        overview=findViewById(R.id.aoverview);
        reldate=findViewById(R.id.areldate);
        tvrating=findViewById(R.id.rating);
        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycler1);
        trecyclerView=findViewById(R.id.recycler2);
        materialFavoriteButton=findViewById(R.id.favid);
        arrayTrailer=new ArrayList<>();


        String[] s=getIntent().getStringArrayExtra("movie");
        tit.setText(s[0]);
        setTitle(s[0]);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + s[1]).placeholder(R.mipmap.ic_launcher).into(poster);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + s[2]).placeholder(R.mipmap.ic_launcher).into(backdrop);
        overview.setText("OverView"+s[3]);
        reldate.setText(s[4]);
        tvrating.setText(s[5]);
        favtit=s[0];
        favback=s[2];
        favpost=s[1];
        favdes=s[3];
        favrel=s[4];
        favrating=s[5];
        favmovieid=s[6];

        rev="https://api.themoviedb.org/3/movie/"+s[6]+"/reviews?api_key=90a433e174171dcdcbf2f7afa8364262";
        trailer="https://api.themoviedb.org/3/movie/"+s[6]+"/videos?api_key=90a433e174171dcdcbf2f7afa8364262";
        new ReviewTask().execute(rev);
        recyclerView.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        new TrailerTask().execute(trailer);

        trecyclerView.setLayoutManager(new LinearLayoutManager(MovieActivity.this));

        String savestate;
        savestate=MainActivity.movieViewModel.getIdread(favmovieid);
        if(savestate!=null){
            materialFavoriteButton.setFavorite(true,true);
        }
        else{
            materialFavoriteButton.setFavorite(false,true);
        }
        materialFavoriteButton.setOnFavoriteChangeListener(     new MaterialFavoriteButton.OnFavoriteChangeListener() {

            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {

                MyModel myModel=new MyModel();
                if (favorite){
                    myModel.setTitle(favtit);
                    myModel.setBackdroppath(favback);
                    myModel.setPosterpath(favpost);
                    myModel.setOverview(favdes);
                    myModel.setReleasedate(favrel);
                    myModel.setRating(favrating);
                    myModel.setId(favmovieid);
                    materialFavoriteButton.setFavorite(true);
                    MainActivity.movieViewModel.insertData(myModel);

                }
                else{
                    myModel.setId(favmovieid);
                    MainActivity.movieViewModel.deleteData(myModel);
                }
            }
        });




    }
    public class ReviewTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
               URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return scanner.next();
                }
                else{
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("string", String.valueOf(results));
                    if (results.length() != 0) {
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject1 = results.getJSONObject(i);
                            String content = jsonObject1.getString("content");
                            Log.i("string", String.valueOf(content));
                            arrayList.add(new ModelClass(content));
                        }
                    } else {
                        String content = "Review Not Available";
                        arrayList.add(new ModelClass(content));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setAdapter(new ReviewAdapter(MovieActivity.this,arrayList));
        }
    }
    public class TrailerTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if(scanner.hasNext()){
                    return scanner.next();
                }
                else{
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray results = jsonObject.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject1 = results.getJSONObject(i);
                            String key = jsonObject1.getString("key");
                            String name = jsonObject1.getString("name");
                            arrayTrailer.add(new TrailerModel("https://www.youtube.com/watch?v="+key,name));
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            trecyclerView.setAdapter(new TrailerAdapter(MovieActivity.this,arrayTrailer));
        }
    }


}
