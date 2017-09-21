package com.example.timmy.quakelogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by timmy on 8/18/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> list){
        super(context,0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        Earthquake earthquake = getItem(position);
        TextView magnitudeView = (TextView)convertView.findViewById(R.id.magnitude_text);
        TextView locationView = (TextView) convertView.findViewById(R.id.location_text);
        TextView timeView = (TextView)convertView.findViewById(R.id.time_text);
        TextView dateView = (TextView)convertView.findViewById(R.id.date_text);
        TextView offsetView = (TextView)convertView.findViewById(R.id.offset_text);


        magnitudeView.setText(earthquake.getMag());
        locationView.setText(earthquake.getLocation());
        timeView.setText(earthquake.getTime());
        dateView.setText(earthquake.getDate());
        offsetView.setText(earthquake.getOffset());

        //setting magnitude background
        GradientDrawable background = (GradientDrawable) magnitudeView.getBackground();
        background.setColor(getMagnitudeColor(earthquake.getMag()));

        return convertView;
    }

    private int getMagnitudeColor(String magnitude){
        int magColorId;
        switch ((int) Double.parseDouble(magnitude)){
            case 0:
            case 1:
                magColorId = R.color.magnitude1;
                break;
            case 2:
                magColorId = R.color.magnitude2;
                break;
            case 3:
                magColorId = R.color.magnitude3;
                break;
            case 4:
                magColorId = R.color.magnitude4;
                break;
            case 5:
                magColorId = R.color.magnitude5;
                break;
            case 6:
                magColorId = R.color.magnitude6;
                break;
            case 7:
                magColorId = R.color.magnitude7;
                break;
            case 8:
                magColorId = R.color.magnitude8;
                break;
            case 9:
                magColorId = R.color.magnitude9;
                break;
            default:
                magColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorId);
    }
}
