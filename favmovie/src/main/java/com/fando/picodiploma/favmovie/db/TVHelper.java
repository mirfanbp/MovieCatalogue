package com.fando.picodiploma.favmovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsTV.TABLE_NAME_TV;

public class TVHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_TV;
    private static DatabaseHelper dataBaseHelper;
    private static TVHelper INSTANCE;

    private static SQLiteDatabase database;

    private TVHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TVHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen()) database.close();
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
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new
                String[]{id});
    }

}
