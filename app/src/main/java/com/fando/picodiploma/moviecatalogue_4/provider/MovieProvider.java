package com.fando.picodiploma.moviecatalogue_4.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract;
import com.fando.picodiploma.moviecatalogue_4.db.MovieHelper;
import com.fando.picodiploma.moviecatalogue_4.db.TVHelper;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.AUTHORITY;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.CONTENT_URI;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.TABLE_NAME_MOV;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.CONTENT_URI_TV;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.TABLE_NAME_TV;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 11;
    private static final int MOVIE_ID = 12;
    private static final int TV = 21;
    private static final int TV_ID = 22;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME_MOV, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.FavouriteColumnsMovie.TABLE_NAME_MOV + "/#",
                MOVIE_ID);
    }

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME_TV, TV);
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.FavouriteColumnsTV.TABLE_NAME_TV + "/#",
                TV_ID);
    }

    private MovieHelper movieHelper;
    private TVHelper tvHelper;

    public MovieProvider() {
    }

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        tvHelper = new TVHelper(getContext());
        tvHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryAllProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryAllProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null)
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        Uri contentUri = null;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = movieHelper.insertProvider(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI, added);
                }
                break;
            case TV:
                added = tvHelper.insertProvider(values);
                if (added > 0) {
                    contentUri = ContentUris.withAppendedId(CONTENT_URI_TV, added);
                }
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return contentUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return deleted;
    }

}
