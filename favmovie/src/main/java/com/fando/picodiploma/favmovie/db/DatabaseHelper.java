package com.fando.picodiploma.favmovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FAV_MOV =
            String.format("CREATE TABLE %s" +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s REAL NOT NULL," + "" +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    DatabaseContract.FavouriteColumnsMovie.TABLE_NAME_MOV,
                    DatabaseContract.FavouriteColumnsMovie._ID,
                    DatabaseContract.FavouriteColumnsMovie.TITLE,
                    DatabaseContract.FavouriteColumnsMovie.RELEASE_DATE,
                    DatabaseContract.FavouriteColumnsMovie.VOTE_AVERAGE,
                    DatabaseContract.FavouriteColumnsMovie.ORIGINAL_LANGUAGE,
                    DatabaseContract.FavouriteColumnsMovie.OVERVIEW,
                    DatabaseContract.FavouriteColumnsMovie.POSTER,
                    DatabaseContract.FavouriteColumnsMovie.BACKDROP_PATH
            );
    private static final String SQL_CREATE_TABLE_FAV_TV =
            String.format("CREATE TABLE %s" +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s REAL NOT NULL," + "" +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    DatabaseContract.FavouriteColumnsTV.TABLE_NAME_TV,
                    DatabaseContract.FavouriteColumnsTV._ID,
                    DatabaseContract.FavouriteColumnsTV.NAME,
                    DatabaseContract.FavouriteColumnsTV.FIRST_AIR_DATE,
                    DatabaseContract.FavouriteColumnsTV.VOTE_AVERAGE,
                    DatabaseContract.FavouriteColumnsTV.ORIGINAL_LANGUAGE,
                    DatabaseContract.FavouriteColumnsTV.OVERVIEW,
                    DatabaseContract.FavouriteColumnsTV.POSTER,
                    DatabaseContract.FavouriteColumnsTV.BACKDROP_PATH
            );
    private static String DATABASE_NAME = "dbmoviecatalogue";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAV_MOV);
        db.execSQL(SQL_CREATE_TABLE_FAV_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavouriteColumnsMovie.TABLE_NAME_MOV);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavouriteColumnsTV.TABLE_NAME_TV);
        onCreate(db);
    }
}
