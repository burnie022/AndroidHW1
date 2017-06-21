package com.example.burnie.newsapp;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;

import android.util.Log;



public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String NEWS_BASE_URL =
            //"https://newsapi.org/v1/articles?source=";
            "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest";


    // ADD YOUR API KEY IN THE STRING BELOW
    private static final String apiKey =
            "";


    final static String QUERY_PARAM = "apiKey";


    public static URL buildUrl() {

        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, apiKey)
//                .appendPath(NEWS_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
