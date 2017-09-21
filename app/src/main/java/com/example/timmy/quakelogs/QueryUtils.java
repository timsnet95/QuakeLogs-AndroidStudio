package com.example.timmy.quakelogs;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.example.timmy.quakelogs.MainActivity.LOG_TAG;

/**
 * Created by timmy on 8/18/2017.
 */

public final class QueryUtils {
    public static ArrayList<Earthquake> fetchEarthquakeData(String urlLink){
        URL url = createUrl(urlLink);
        String jsonString = makeHttpRequest(url);
        return extractEarthquakes(jsonString);
    }
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Earthquake> extractEarthquakes(String jsonString) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject source = new JSONObject(jsonString);
            JSONArray features = source.getJSONArray("features");
            for(int i = 0; i<features.length(); i++) {
                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                Earthquake earthquake = new Earthquake(properties.getString("mag"),properties.getString("place"),properties.getString("time"),properties.getString("url"));
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    //returns null if invalid url
    private static URL createUrl(String urlLink){
        URL url = null;
        if(!TextUtils.isEmpty(urlLink)){
            try {
                url = new URL(urlLink);
            } catch (MalformedURLException e){
                Log.e(LOG_TAG,e.getMessage(),e);
            }
        }
        return url;
    }

    //returns "" if invalid request
    private static String makeHttpRequest(URL url){
        String jsonString = "";
        if(url == null)
            return jsonString;

        HttpsURLConnection urlConnection = null;
        InputStream inputStream;
        try{
            urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonString = readFromStream(inputStream);
            }
        } catch (IOException ioe){
            Log.e(LOG_TAG,ioe.getMessage());
        }
        return jsonString;
    }

    private static String readFromStream(InputStream inputStream){
        StringBuilder builder = new StringBuilder();
        if(inputStream == null)
            return  "";
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line !=null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException ioe){
            Log.e(LOG_TAG,ioe.getMessage());
        }
        return builder.toString();
    }
}
