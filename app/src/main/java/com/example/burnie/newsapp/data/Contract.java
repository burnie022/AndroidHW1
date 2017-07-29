package com.example.burnie.newsapp.data;

import android.provider.BaseColumns;

public class Contract {

    public static class TABLE_NEWS implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PUBLISHEDDATE = "publishedAt";
        public static final String COLUMN_NAME_URLTOIMAGE = "urlToImage";
        public static final String COLUMN_NAME_URL = "url";
    }
}
