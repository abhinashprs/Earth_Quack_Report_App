package com.example.quakereport;


import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.widget.TextView;

import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthQuakeAdapter mAdapter;
    TextView emptyStateTextView;


    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);




        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        mAdapter = new EarthQuakeAdapter(EarthquakeActivity.this, new ArrayList<EarthQuake>());


        earthquakeListView.setAdapter(mAdapter);
        emptyStateTextView = (TextView) findViewById(R.id.emptyView);
        earthquakeListView.setEmptyView(emptyStateTextView);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuake currentEarthQuake = mAdapter.getItem(position);
                Uri earthQuakeUri = Uri.parse(currentEarthQuake.getUrl());
                Intent webSite = new Intent(Intent.ACTION_VIEW, earthQuakeUri);
                startActivity(webSite);
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, EarthquakeActivity.this);
        }else {
            View loadingIndicator = findViewById(R.id.loadingIndicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthQuakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> earthQuakes) {
        View loadingIndicator= findViewById(R.id.loadingIndicator);
        loadingIndicator.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_earthquake);

        mAdapter.clear();
        if (earthQuakes != null && !earthQuakes.isEmpty()) {
            mAdapter.addAll(earthQuakes);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        mAdapter.clear();

    }


}