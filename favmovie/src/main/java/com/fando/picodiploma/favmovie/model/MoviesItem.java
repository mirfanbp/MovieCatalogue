package com.fando.picodiploma.favmovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.BACKDROP_PATH;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.ORIGINAL_LANGUAGE;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.OVERVIEW;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.POSTER;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.RELEASE_DATE;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.TITLE;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsMovie.VOTE_AVERAGE;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.getColumnsDouble;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.getColumnsInt;
import static com.fando.picodiploma.favmovie.db.DatabaseContract.getColumnsString;

public class MoviesItem implements Parcelable {
    public static final Creator<MoviesItem> CREATOR = new Creator<MoviesItem>() {
        @Override
        public MoviesItem createFromParcel(Parcel in) {
            return new MoviesItem(in);
        }

        @Override
        public MoviesItem[] newArray(int size) {
            return new MoviesItem[size];
        }
    };
    private String overview;
    @SerializedName("original_language")
    private String originalLanguage;
    private String title;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    // @SerializedName = harus sama dgn field api
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAverage;
    private int id;

    public MoviesItem(Parcel in) {
        overview = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        id = in.readInt();
    }

    public MoviesItem() {

    }

    public MoviesItem(Cursor cursor) {
        this.id = getColumnsInt(cursor, _ID);
        this.title = getColumnsString(cursor, TITLE);
        this.releaseDate = getColumnsString(cursor, RELEASE_DATE);
        this.voteAverage = getColumnsDouble(cursor, VOTE_AVERAGE);
        this.originalLanguage = getColumnsString(cursor, ORIGINAL_LANGUAGE);
        this.overview = getColumnsString(cursor, OVERVIEW);
        this.posterPath = getColumnsString(cursor, POSTER);
        this.backdropPath = getColumnsString(cursor, BACKDROP_PATH);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "com.fando.picodiploma.moviecatalogue_api.model.MoviesItem{" +
                        "overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",title = '" + title + '\'' +
                        ",genre_ids = '" + genreIds + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
