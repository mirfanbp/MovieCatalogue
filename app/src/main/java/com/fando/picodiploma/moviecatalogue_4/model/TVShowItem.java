package com.fando.picodiploma.moviecatalogue_4.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.BACKDROP_PATH;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.FIRST_AIR_DATE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.NAME;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.ORIGINAL_LANGUAGE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.OVERVIEW;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.POSTER;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.VOTE_AVERAGE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.getColumnsDouble;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.getColumnsInt;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.getColumnsString;

public class TVShowItem implements Parcelable {
    @SerializedName("first_air_date")
    private String firstAirDate;

    private String overview;
    @SerializedName("original_language")
    private String originalLanguage;

    private List<Integer> genreIds;
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_average")
    private double voteAverage;

    private String name;
    private int id;

    public TVShowItem(Parcel in) {
        firstAirDate = in.readString();
        overview = in.readString();
        originalLanguage = in.readString();
        posterPath = in.readString();
        originCountry = in.createStringArrayList();
        backdropPath = in.readString();
        voteAverage = in.readDouble();
        name = in.readString();
        id = in.readInt();
    }

    public TVShowItem(Cursor cursor) {
        this.id = getColumnsInt(cursor, _ID);
        this.name = getColumnsString(cursor, NAME);
        this.firstAirDate = getColumnsString(cursor, FIRST_AIR_DATE);
        this.voteAverage = getColumnsDouble(cursor, VOTE_AVERAGE);
        this.originalLanguage = getColumnsString(cursor, ORIGINAL_LANGUAGE);
        this.overview = getColumnsString(cursor, OVERVIEW);
        this.posterPath = getColumnsString(cursor, POSTER);
        this.backdropPath = getColumnsString(cursor, BACKDROP_PATH);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstAirDate);
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(posterPath);
        dest.writeStringList(originCountry);
        dest.writeString(backdropPath);
        dest.writeDouble(voteAverage);
        dest.writeString(name);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TVShowItem> CREATOR = new Parcelable.Creator<TVShowItem>() {
        @Override
        public TVShowItem createFromParcel(Parcel in) {
            return new TVShowItem(in);
        }

        @Override
        public TVShowItem[] newArray(int size) {
            return new TVShowItem[size];
        }
    };

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "TVShowItem{" +
                        "first_air_date = '" + firstAirDate + '\'' +
                        ",overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",genre_ids = '" + genreIds + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",origin_country = '" + originCountry + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
