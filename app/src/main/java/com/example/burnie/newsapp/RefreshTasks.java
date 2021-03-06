/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.burnie.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";

/*
This method has been added to periodically refresh the database to keep updated information on the screen
 */

    public static void refreshNewsArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.buildUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            //result = NetworkUtils.parseJSON(json);
            result = MainActivity.getSimpleNewsStringsFromJson(context, json);
            DatabaseUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}