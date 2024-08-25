package com.example.quakereport;

import android.content.Context;

import android.content.AsyncTaskLoader;

import androidx.loader.content.*;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    String mUrl;

    public EarthQuakeLoader(Context context, String url) {
        //noinspection deprecation
        super(context);
        mUrl = url;
    }

    /**
     * @noinspection deprecation
     */
    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    /**
     * @noinspection deprecation
     */
    @Override
    public List<EarthQuake> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        List<EarthQuake> earthQuakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthQuakes;
    }
}
