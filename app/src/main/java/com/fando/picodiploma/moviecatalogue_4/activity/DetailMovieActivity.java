package com.fando.picodiploma.moviecatalogue_4.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.db.MovieHelper;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;
import com.fando.picodiploma.moviecatalogue_4.widget.FavAppWidget;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.BACKDROP_PATH;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.CONTENT_URI;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.ORIGINAL_LANGUAGE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.OVERVIEW;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.POSTER;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.RELEASE_DATE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.TITLE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.VOTE_AVERAGE;

public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    private NestedScrollView scrollView;
    private ImageButton btnFavMovie, btnDelFavMovie;
    private ImageView imgPoster, imgBg;
    private TextView tvDetailTitle, tvReleaseDate, tvRating, tvLanguage, tvOverview;
    private ProgressBar progressBar;
    private boolean isFavourited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.detail_movie);

        MoviesItem moviesItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
//        String idFavMovie = Integer.toString(moviesItem.getId());

        scrollView = findViewById(R.id.scroll_view_container);
        tvDetailTitle = findViewById(R.id.tv_detail_title_tvs);
        tvReleaseDate = findViewById(R.id.tv_release_date_detail_tvs);
        tvRating = findViewById(R.id.tv_rating_detail_tvs);
        tvLanguage = findViewById(R.id.tv_language_detail_tvs);
        tvOverview = findViewById(R.id.tv_overview_tvs);
        imgPoster = findViewById(R.id.img_poster_path_tvs);
        imgBg = findViewById(R.id.img_bg_tvs);
        progressBar = findViewById(R.id.progressBar_detail_mv);

        btnFavMovie = findViewById(R.id.btn_fav_mov);
        btnDelFavMovie = findViewById(R.id.btn_del_fav_mov);
        btnFavMovie.setOnClickListener(this);
        btnDelFavMovie.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);
        btnFavMovie.setVisibility(View.VISIBLE);

        if (isFavourited) {
            btnFavMovie.setVisibility(View.VISIBLE);
            btnDelFavMovie.setVisibility(View.GONE);
        } else {
            btnFavMovie.setVisibility(View.GONE);
            btnDelFavMovie.setVisibility(View.VISIBLE);
        }

        loadFavMovie();
        getMovie();

    }

    private void getMovie() {
        MoviesItem moviesItem = getIntent().getParcelableExtra(EXTRA_MOVIE);

        progressBar.setVisibility(View.INVISIBLE);
        String rating = Double.toString(moviesItem.getVoteAverage());
        tvDetailTitle.setText(moviesItem.getTitle());
        tvReleaseDate.setText(moviesItem.getReleaseDate());
        tvRating.setText(rating);
        tvLanguage.setText(moviesItem.getOriginalLanguage());
        tvOverview.setText(moviesItem.getOverview());
        Glide.with(DetailMovieActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + moviesItem.getPosterPath())
                .apply(new RequestOptions().transform(new RoundedCorners(12)))
                .into(imgPoster);
        Glide.with(DetailMovieActivity.this)
                .load("https://image.tmdb.org/t/p/w342" + moviesItem.getBackdropPath())
                .into(imgBg);
        scrollView.setAnimation(AnimationUtils.loadAnimation(DetailMovieActivity.this, R.anim.slide_up));

        progressBar.setVisibility(View.GONE);
    }

    private void loadFavMovie() {
        MoviesItem moviesItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        MovieHelper movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        Cursor cursor = getContentResolver().query(Uri.parse(
                CONTENT_URI + "/" + moviesItem.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavourited = true;
            cursor.close();
        }

        if (isFavourited) {
            btnFavMovie.setVisibility(View.GONE);
            btnDelFavMovie.setVisibility(View.VISIBLE);
        } else {
            btnFavMovie.setVisibility(View.VISIBLE);
            btnDelFavMovie.setVisibility(View.GONE);
        }
    }

    private void addFavourite() {
        MoviesItem moviesItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        ContentValues values = new ContentValues();

        values.put(TITLE, moviesItem.getTitle());
        values.put(RELEASE_DATE, moviesItem.getReleaseDate());
        values.put(VOTE_AVERAGE, moviesItem.getVoteAverage());
        values.put(ORIGINAL_LANGUAGE, moviesItem.getOriginalLanguage());
        values.put(OVERVIEW, moviesItem.getOverview());
        values.put(POSTER, moviesItem.getPosterPath());
        values.put(BACKDROP_PATH, moviesItem.getBackdropPath());
        getContentResolver().insert(CONTENT_URI, values);
        updateWidget(this);
    }

    private void removeFavourite() {
        MoviesItem moviesItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        getContentResolver().delete(Uri.parse(
                CONTENT_URI + "/" + moviesItem.getId()),
                null,
                null
        );
        updateWidget(this);
    }

    private void updateWidget(Context context) {
        Intent intent = new Intent(context, FavAppWidget.class);
        intent.setAction(FavAppWidget.UPDATE_WIDGET);
        context.sendBroadcast(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fav_mov) {
            addFavourite();
            btnFavMovie.setVisibility(View.GONE);
            btnDelFavMovie.setVisibility(View.VISIBLE);
            Snackbar.make(view, R.string.add_favourite, Snackbar.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_del_fav_mov) {
            removeFavourite();
            btnFavMovie.setVisibility(View.VISIBLE);
            btnDelFavMovie.setVisibility(View.GONE);
            Snackbar.make(view, R.string.remove_favourite, Snackbar.LENGTH_SHORT).show();
        }
    }
}

