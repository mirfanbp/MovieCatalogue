package com.fando.picodiploma.moviecatalogue_4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.fando.picodiploma.moviecatalogue_4";
    private static final String SCHEME = "content";

    public static int getColumnsInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static String getColumnsString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static double getColumnsDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static final class FavouriteColumnsMovie implements BaseColumns {
        public static final String TABLE_NAME_MOV = "fav_movie";
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME_MOV).build();
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String VOTE_AVERAGE = "vote_average";
        public static String ORIGINAL_LANGUAGE = "original_language";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String BACKDROP_PATH = "backdrop_path";
    }

    public static final class FavouriteColumnsTV implements BaseColumns {
        public static final String TABLE_NAME_TV = "fav_tv";
        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME_TV).build();
        public static String NAME = "name";
        public static String FIRST_AIR_DATE = "first_air_date";
        public static String VOTE_AVERAGE = "vote_average";
        public static String ORIGINAL_LANGUAGE = "original_language";
        public static String OVERVIEW = "overview";
        public static String POSTER = "poster";
        public static String BACKDROP_PATH = "backdrop_path";
    }

}