package com.example.burnie.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView mSearchResultsTextView;
    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_news_data);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }


    public class FetchNewsQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            //String newsService = params[0];
            URL newsRequestUrl = NetworkUtils.buildUrl();

            String jsonNewsResponse = null;

            try {
                jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return  jsonNewsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {

                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mSearchResultsTextView.setText(s);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sources, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            new FetchNewsQueryTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
