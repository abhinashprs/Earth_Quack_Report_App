package com.example.quakereport;

public class EarthQuake {
    private final double mMagnitude;
    private final String mPlace;
    private final long mDate;
    private final String mUrl;


    public EarthQuake(double magnitude, String place, long date, String url){
        mPlace=place;
        mMagnitude=magnitude;
        mDate=date;
        mUrl=url;



    }
    public double getMaganitude(){
        return mMagnitude;
    }
    public String getPlace(){
        return mPlace;
    }
    public long getDate(){
        return mDate;
    }
    public String getUrl(){return mUrl; };

}
