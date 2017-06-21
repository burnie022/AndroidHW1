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
            "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=b6dbc3e3786a4f0196022cda1a763c97";

//    private static final String NEWS_TAIL_URL =
//            "&sortBy=latest&apiKey=b6dbc3e3786a4f0196022cda1a763c97";
//            "b6dbc3e3786a4f0196022cda1a763c97";

    final static String QUERY_PARAM = "q";


    public static URL buildUrl() {

        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
//                .appendQueryParameter(QUERY_PARAM, newsQuery)
                //.appendPath(NEWS_TAIL_URL)
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
