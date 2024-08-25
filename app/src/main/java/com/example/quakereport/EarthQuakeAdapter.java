package com.example.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake>{
    private static final String LOCATOIN_SEPRATER=" of ";
    public EarthQuakeAdapter(Context context, List<EarthQuake> earthQuakes) {
        super( context,0,earthQuakes );
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from( getContext()).inflate( R.layout.earthquake_list_item,parent,false );
        }
        EarthQuake currentEarthquake = getItem( position );
        TextView meganitudeView = listItemView.findViewById( R.id.magnitude );
        assert currentEarthquake != null;

        String formattedMagnitude =  formatMagnitude(currentEarthquake.getMaganitude());
        GradientDrawable magnitudeCircle =(GradientDrawable) meganitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMaganitude());
        magnitudeCircle.setColor( magnitudeColor );


        meganitudeView.setText(formattedMagnitude);

        String orgLocation=currentEarthquake.getPlace();
        String locationOffset;
        String primaryLocation;
        if (orgLocation.contains( LOCATOIN_SEPRATER )){
            String[] parts =orgLocation.split( LOCATOIN_SEPRATER );
            locationOffset = parts[0]+LOCATOIN_SEPRATER;
            primaryLocation= parts[1];

        }
        else {
            locationOffset=getContext().getString(R.string.near_the);
            primaryLocation=orgLocation;


        }


        TextView locationOfSet = listItemView.findViewById( R.id.location_of_set );
        locationOfSet.setText(locationOffset);
        TextView locationPrimary = listItemView.findViewById( R.id.primary_location );
        locationPrimary.setText(primaryLocation);
        TextView dateView =listItemView.findViewById( R.id.date );

        Date dateObject =new Date(currentEarthquake.getDate());

        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = listItemView.findViewById( R.id.time );
        String formattedTime = formatTime(dateObject);
        timeView.setText( formattedTime );


        return listItemView;
    }
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor( magnitude );
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId =R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId =R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId =R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId =R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId =R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId =R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId =R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId =R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId =R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId =R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor( getContext(), magnitudeColorResourceId );

    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd,yyyy");
        return dateFormat.format( dateObject );

    }
    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h : mm a");
        return timeFormat.format( dateObject );

    }
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format( magnitude );


    }


}
