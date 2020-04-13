package com.fando.picodiploma.moviecatalogue_4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.TABLE_NAME_MOV;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_MOV;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    // inisiasi database.
    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    // membuka koneksi database
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    // menutup koneksi database
    public void close() {
        dataBaseHelper.close();
    }

    public Cursor queryAllProvider() {
        return database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }

}
