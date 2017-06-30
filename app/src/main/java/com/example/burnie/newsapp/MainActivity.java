package com.example.burnie.newsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URL;
import java.util.ArrayList;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //private TextView mSearchResultsTextView;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    private ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
    //private String myJson = "{\"status\":\"ok\",\"source\":\"the-next-web\",\"sortBy\":\"latest\",\"articles\":[{\"author\":\"Bryan Clark\",\"title\":\"Take better screenshots in macOS with these hidden features\",\"description\":\"One of my favorite Mac features is the ease at which you can take and edit screenshots using Apple's default tools. Using one of several keyboard shortcuts, I can take a screenshot ...\",\"url\":\"https://thenextweb.com/apple/2017/06/20/take-better-screenshots-in-macos-with-these-hidden-features/\",\"urlToImage\":\"https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/Screen-Shot-2017-06-20-at-1.30.21-PM.jpg\",\"publishedAt\":\"2017-06-20T22:46:51Z\"},{\"author\":\"Tristan Greene\",\"title\":\"Love letter: local co-op games bring us closer together\",\"description\":\"The reports of the death of local cooperative gaming have been greatly exaggerated. Developers at E3 showed off titles like A Way Out, Moonhunters, and Cuphead – all designed with ...\",\"url\":\"https://thenextweb.com/gaming/2017/06/20/love-letter-local-co-op-games-bring-us-closer-together/\",\"urlToImage\":\"https://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/couch.jpg\",\"publishedAt\":\"2017-06-20T22:03:36Z\"},{\"author\":\"Napier Lopez\",\"title\":\"The OnePlus 5’s best feature is its ‘reading mode’\",\"description\":\"The OnePlus 5 became official today, and we wrote a a review earlier today, which you can check out here. But I want to highlight one particular feature of a phone that, however small, ...\",\"url\":\"https://thenextweb.com/gadgets/2017/06/20/the-oneplus-5s-best-feature-is-its-reading-mode/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/OnePlus-Reading-Mode-1-of-1.jpg\",\"publishedAt\":\"2017-06-20T21:57:51Z\"},{\"author\":\"Tristan Greene\",\"title\":\"Shopping site Wish shows your friends and co-workers the kinky shit you’re into\",\"description\":\"The discount shopping site Wish exists to fill the space between Amazon and Etsy. You're not going to find the new iPhone on it and you aren't going to find a hand-knitted tea-cozy ...\",\"url\":\"https://thenextweb.com/insider/2017/06/20/be-careful-what-you-wish-for-shopping-site-offers-no-privacy/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/privacy.jpg\",\"publishedAt\":\"2017-06-20T19:36:29Z\"},{\"author\":\"Napier Lopez\",\"title\":\"Review: The OnePlus 5 is the most exceptional phone you'll find under $500\",\"description\":\"With the OnePlus 3 and 3T, OnePlus really hit its stride. The company that first marketed its budget-friendly phones as so remarkable to be better than everyone else’s flagships had toned back some of the hyperbolic language and let its devices speak for themselves. That was the right move. OnePlus buyers tend to be a …\",\"url\":\"https://thenextweb.com/reviews/2017/06/20/review-the-oneplus-5-is-the-most-exceptional-phone-youll-find-under-500-and-perhaps-more/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/OnePlus-4-of-11.jpg\",\"publishedAt\":\"2017-06-20T20:11:20Z\"},{\"author\":\"Tristan Greene\",\"title\":\"Co-Pilot’s AI finds user-generated content that doesn’t suck\",\"description\":\"The people behind Stackla, a program that helps you stream-line the process of finding and using user-generated content (UGC), are releasing Co-Pilot. A new program that will use machine-learning ...\",\"url\":\"https://thenextweb.com/artificial-intelligence/2017/06/20/co-pilots-ai-finds-user-generated-content-that-doesnt-suck/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/copilot.jpg\",\"publishedAt\":\"2017-06-20T18:15:28Z\"},{\"author\":\"Napier Lopez\",\"title\":\"Instagram now lets you add live video replays to your Stories\",\"description\":\"Both Stories and Live video have become increasingly popular on Instagram, but your life footage can go wasted if few people actually tune in while you're broadcasting.\\r\\n\\r\\nFret no more: ...\",\"url\":\"https://thenextweb.com/facebook/2017/06/20/instagram-now-lets-add-live-video-replays-stories/\",\"urlToImage\":\"https://cdn2.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/Instagram-Live.jpg\",\"publishedAt\":\"2017-06-20T18:02:40Z\"},{\"author\":\"Matthew Hughes\",\"title\":\"MongoDB launches Stitch to put the RAD in non-RelAtional Databases\",\"description\":\"Today at MongoDB World in Chicago, MongoDB announced the launch of Stitch, its new backend-as-a-service (BaaS) tool that lets developers build MongoDB-powered applications, without ...\",\"url\":\"https://thenextweb.com/dd/2017/06/20/mongodb-launches-stitch-put-rad-non-relational-databases/\",\"urlToImage\":\"https://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/stitch_pipelines_1024.png\",\"publishedAt\":\"2017-06-20T18:00:49Z\"},{\"author\":\"TNW Deals\",\"title\":\"Trick out your Mac with the World’s Biggest Mac App Bundle — and pay what you want\",\"description\":\"Cool new apps for your Apple Mac are rolling out to excited users every day. Some help your rig run faster, some better protect your valuable information, and some are just darn fun. ...\",\"url\":\"https://thenextweb.com/offers/2017/06/20/trick-mac-worlds-biggest-mac-app-bundle-pay-want/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/06/wWTSBNP.jpg\",\"publishedAt\":\"2017-06-20T17:33:58Z\"},{\"author\":\"Mix\",\"title\":\"Netflix launches new ‘interactive shows’ that let viewers dictate the story\",\"description\":\"Netflix is putting a new spin on the old Gamebook genre.\\r\\n\\r\\nToday the company announced that it's launching an all-new interactive format that ultimately turns viewers into legitimate ...\",\"url\":\"https://thenextweb.com/apps/2017/06/20/netflix-interactive-show-viewer-story/\",\"urlToImage\":\"https://cdn3.tnwcdn.com/wp-content/blogs.dir/1/files/2017/05/Netflix-Android.jpg\",\"publishedAt\":\"2017-06-20T16:18:32Z\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        //mSearchResultsTextView = (TextView) findViewById(R.id.tv_news_data);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mNewsAdapter);
    }


    public class FetchNewsQueryTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            //String newsService = params[0];
            URL newsRequestUrl = NetworkUtils.buildUrl();

            String jsonNewsResponse = null;

            try {
                jsonNewsResponse = NetworkUtils
                        .getResponseFromHttpUrl(newsRequestUrl);

                //jsonNewsResponse = myJson;
                String[] simpleJsonNewsData = getSimpleNewsStringsFromJson(MainActivity.this, jsonNewsResponse);

                return simpleJsonNewsData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            //return  jsonNewsResponse;

        }

        @Override
        protected void onPostExecute(String[] s) {
            if (s != null) {

                mLoadingIndicator.setVisibility(View.INVISIBLE);
                //mSearchResultsTextView.setText(s);
                mNewsAdapter.setNewsData(s);
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
            //mNewsAdapter.setNewsData(null);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String[] getSimpleNewsStringsFromJson(Context context, String newsJsonStr)
            throws JSONException {



        final String NEWS_LIST = "list";
        final String NEWS_ARTICLES = "articles";
        String[] parsedNewsData = null;

        JSONObject newsJson = new JSONObject(newsJsonStr);
//        JSONObject newsObject = new JSONObject(newsJsonStr);


        JSONArray newsArray = newsJson.getJSONArray(NEWS_ARTICLES);
//        JSONArray newsArray = newsObject.getJSONArray(NEWS_ARTICLES);

        parsedNewsData = new String[newsArray.length()];


        for (int i = 0; i < newsArray.length(); i++) {
            NewsItem newsitem = new NewsItem();
            JSONObject newsObject = newsArray.getJSONObject(i);


            newsitem.setAuthor(newsObject.getString("author"));
            newsitem.setTitle(newsObject.getString("title"));
            newsitem.setDescription(newsObject.getString("description"));
            newsitem.setUrl(newsObject.getString("url"));
            newsitem.setUrlToImage(newsObject.getString("urlToImage"));
            newsitem.setPublishedAt(newsObject.getString("publishedAt"));

            newsItems.add(newsitem);


            parsedNewsData[i] = "Author: " + newsitem.getAuthor() +
                    "\nTitle: " + newsitem.getTitle() +
                    "\nDescription: " + newsitem.getDescription() +
                    "\nURL: " + newsitem.getUrl() +
                    "\nURL To Image: " + newsitem.getUrlToImage() +
                    "\nPublished At: " + newsitem.getPublishedAt() + "\n";

        }

        return parsedNewsData;
    }
}
