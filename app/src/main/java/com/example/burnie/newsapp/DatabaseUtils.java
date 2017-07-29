package com.example.burnie.newsapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_AUTHOR;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_PUBLISHEDDATE;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_TITLE;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_URL;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.COLUMN_NAME_URLTOIMAGE;
import static com.example.burnie.newsapp.data.Contract.TABLE_NEWS.TABLE_NAME;


/*
This class has been added to give us the ability to alter the database as well as
create a cursor.
Bulkinsert takes in the arraylist of news items and places them into their respective rows in the
database
deleteall removes the table from the database
 */

public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHEDDATE + " DESC"
        );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsItems) {

        db.beginTransaction();
        try {
            for (NewsItem a : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_AUTHOR, a.getAuthor());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_PUBLISHEDDATE, a.getPublishedAt());
                cv.put(COLUMN_NAME_URLTOIMAGE, a.getUrlToImage());
                cv.put(COLUMN_NAME_URL, a.getUrl());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

}
