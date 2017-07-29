package com.example.burnie.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.burnie.newsapp.data.Contract;


import java.net.URL;
import java.util.ArrayList;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener{

    private static final int NEWS_LOADER = 1;
    private static final int SEARCH_LOADER = 1;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private Cursor cursor;
    private SQLiteDatabase db;

/*
Removed arraylist, added support for sqlite database and cursor
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_news);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //mNewsAdapter = new NewsAdapter();

        /*
        Added shared prefs for first time running to create database

         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);

//   No longer needed now that cursor is used in NewsAdapter
//        mNewsAdapter = new NewsAdapter(newsItems, new NewsAdapter.ItemClickListener(){
//                    @Override
//                    public void onItemClick(int clickedItemIndex) {
//                        String url = newsItems.get(clickedItemIndex).getUrl();
//                        //Log.d(TAG, String.format("Url %s", url));
//
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        if (intent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(intent);
//                        }
//                        startActivity(intent);
//                    }
//
//                });
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    /*
    onCreate is first overridden method for asynctask loader
     */
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshTasks.refreshNewsArticles(MainActivity.this);
                return null;
            }
        };
    }

    /*
    A few more overridden method for asynctask loader
     */
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        mLoadingIndicator.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);

        mNewsAdapter = new NewsAdapter(cursor, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {}

    /*
    Here an Intent is used to send out a URL and start a browser activity
     */
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
        Log.d("mainactivity", String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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

            load();
        }
        return true;
    }

    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }

    /*

    This method is used to parse the json. It has been updated to return an arraylist of newsitem.

     */

    public static ArrayList<NewsItem> getSimpleNewsStringsFromJson(Context context, String newsJsonStr)
            throws JSONException {

        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
        final String NEWS_LIST = "list";
        final String NEWS_ARTICLES = "articles";

        JSONObject newsJson = new JSONObject(newsJsonStr);
        JSONArray newsArray = newsJson.getJSONArray(NEWS_ARTICLES);

        for (int i = 0; i < newsArray.length(); i++) {
            NewsItem newsitem = new NewsItem();
            JSONObject newsObject = newsArray.getJSONObject(i);

            Log.d("", "Received json data");

            newsitem.setAuthor(newsObject.getString("author"));
            newsitem.setTitle(newsObject.getString("title"));
            newsitem.setDescription(newsObject.getString("description"));
            newsitem.setUrl(newsObject.getString("url"));
            newsitem.setUrlToImage(newsObject.getString("urlToImage"));
            newsitem.setPublishedAt(newsObject.getString("publishedAt"));

            newsItems.add(newsitem);


        }

        return newsItems;
    }
}
