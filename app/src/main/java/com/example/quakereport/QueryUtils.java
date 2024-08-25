package com.example.quakereport;

import android.text.TextUtils;
import android.util.Log;

import com.example.quakereport.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    private static final String LOG_TAG =QueryUtils.class.getSimpleName();
    public static List<EarthQuake> fetchEarthquakeData(String requestUrl){
       URL url= createUrl( requestUrl );
       String jsonResponse=null;
       try {
           jsonResponse=makeHttpRequest( url );
       } catch (Exception e) {
           Log.e( LOG_TAG,"Problem making Http request" ,e);
       }
       List<EarthQuake> earthQuakes =extractFeatureFromJson( jsonResponse );
       return earthQuakes;


    }



    private static String makeHttpRequest(URL url){
        String jsonResponse="";
        if(jsonResponse==null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream =null;
        try {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout( 10000 );
            urlConnection.setConnectTimeout( 15000 );
            urlConnection.setRequestMethod( "GET" );
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse= readFromStream(inputStream);

            }else {
                Log.e( LOG_TAG,"Error Response Code " + urlConnection.getResponseCode() );
            }

        } catch (IOException e) {
            Log.e( LOG_TAG,"problem Receive from result JSON Response" ,e );
        }
        finally {
            if (urlConnection !=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e( LOG_TAG, "Problem to Inout output",e );
                }
            }
        }


        return jsonResponse;
    }



    private static URL createUrl(String stringUrl){
        URL url =null;
        try {
            url = new URL( stringUrl );
        }
        catch (MalformedURLException e){
            Log.e( LOG_TAG  , "Problem Building the Url",e );
        }



        return url;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line =reader.readLine();

            while (line!=null){
               output.append( line );
                line= reader.readLine();
            }
        }

        return output.toString();
    }
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    private static List<EarthQuake> extractFeatureFromJson(String earthquakeJSON) {

        if (TextUtils.isEmpty( earthquakeJSON )){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<EarthQuake> earthquakes = new ArrayList<>();


        try {
            JSONObject basicObject = new JSONObject(earthquakeJSON);
            JSONArray dataArray = basicObject.getJSONArray( "features" );
            for (int i=0; i<dataArray.length();i++){
                JSONObject indexObject = dataArray.getJSONObject( i );
                JSONObject properties = indexObject.getJSONObject( "properties" );
                double magnitude = properties.getDouble( "mag" );
                String location = properties.getString( "place" );
                long time = properties.getLong( "time" );
                String url = properties.getString( "url" );


                EarthQuake earthQuake =new EarthQuake( magnitude,location, time , url);
                earthquakes.add( earthQuake );

            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e( "QueryUtils", "Problem parsing the earthquake JSON results", e );
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}