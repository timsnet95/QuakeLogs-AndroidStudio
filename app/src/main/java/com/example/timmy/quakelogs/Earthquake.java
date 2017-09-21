package com.example.timmy.quakelogs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by timmy on 8/18/2017.
 */

public class Earthquake {
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy");
    private static SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
    private static DecimalFormat decimalFormatter = new DecimalFormat("0.0");
    private static final String locationDelim = " of ";
    private static final String defaultOffset = " near ";

    private String mag;
    private String location;
    private String date;
    private String offset;
    private String time;
    private String url;

    public Earthquake(String mag, String fullLocation, String date, String url){
        this.mag = formatDecimal(mag);
        this.date = formatDate(date);
        this.time = formatTime(date);
        this.url = url;
        setLocation(fullLocation);
    }

    public String getUrl(){
        return url;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }

    public String getMag(){
        return mag;
    }

    public String getLocation(){
        return location;
    }

    public String getOffset(){
        return offset;
    }

    private String formatDate(String date)
    {
        Date dateObj = new Date(Long.parseLong(date));
        return dateFormatter.format(dateObj);
    }

    private String formatTime(String date)
    {
        Date dateObj = new Date(Long.parseLong(date));
        return timeFormatter.format(dateObj);
    }

    private void setLocation(String fullLocation){
        if (fullLocation.contains(locationDelim)){
            String[] locationArr = fullLocation.split(locationDelim);
            offset = locationArr[0] + locationDelim;
            location = locationArr[1];
        } else {
            offset = defaultOffset;
            location = fullLocation;
        }
    }

    private String formatDecimal(String magnitude){
        return decimalFormatter.format(Double.parseDouble(magnitude));
    }
}

