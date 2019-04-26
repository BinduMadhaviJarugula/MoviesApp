package com.example.cse.moviedb;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MyModel> arrayList;
    String popularurl = "https://api.themoviedb.org/3/movie/popular?api_key=90a433e174171dcdcbf2f7afa8364262";
    String topratedurl = "https://api.themoviedb.org/3/movie/top_rated?api_key=90a433e174171dcdcbf2f7afa8364262";
    static MovieViewModel movieViewModel;

    private static String list_state="List_state";
    private Parcelable parcelableLayout;
    private static final String bundleLayout="bundle";
    private ArrayList<? extends MyModel> modelstate=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* if (savedInstanceState!=null){
            modelstate=savedInstanceState.getParcelableArrayList(list_state);
            parcelableLayout=savedInstanceState.getParcelable(bundleLayout);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                }

        }*/
        requestQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        arrayList = new ArrayList<>();

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        if (isOnline()) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            }
            popData();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();

            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.show();
        }
    }


    private boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {

            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popolar:
                if (isOnline()) {
                    arrayList.clear();
                    popData();
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    recyclerView.setAdapter(new MyAdapter(this, (ArrayList<MyModel>) arrayList));
                    break;
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.show();
                }
            case R.id.toprated:
                if (isOnline()) {
                    arrayList.clear();
                    topData();
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    recyclerView.setAdapter(new MyAdapter(this, (ArrayList<MyModel>) arrayList));
                    break;
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.show();

                }
            case R.id.fav:
                arrayList.clear();
                movieViewModel.getGetAllFavData().observe(MainActivity.this, new Observer<List<MyModel>>() {
                    @Override
                    public void onChanged(@Nullable List<MyModel> myModels) {
                        if (!myModels.isEmpty()) {
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                            recyclerView.setAdapter(new MyAdapter(MainActivity.this, (ArrayList<MyModel>) myModels));

                        } else {
                            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Movies App");
                            builder.setMessage("No Favourites....");
                            builder.setNegativeButton("Ok", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.show();
                        }
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    public void popData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, popularurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void topData() {
        StringRequest request = new StringRequest(Request.Method.GET, topratedurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    public void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String title = jsonObject1.getString("title");
                String posterpath = jsonObject1.getString("poster_path");
                String backdroppath = jsonObject1.getString("backdrop_path");
                String overview = jsonObject1.getString("overview");
                String releasedate = jsonObject1.getString("release_date");
                String rating = jsonObject1.getString("vote_average");
                String id = jsonObject1.getString("id");

                arrayList.add(new MyModel(title, posterpath, backdroppath, overview, releasedate, rating, id));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new MyAdapter(this, (ArrayList<MyModel>) arrayList));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
