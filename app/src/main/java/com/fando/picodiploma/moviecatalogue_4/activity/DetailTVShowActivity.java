package com.fando.picodiploma.moviecatalogue_4.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.db.TVHelper;
import com.fando.picodiploma.moviecatalogue_4.model.TVShowItem;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.BACKDROP_PATH;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.CONTENT_URI_TV;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.FIRST_AIR_DATE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.NAME;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.ORIGINAL_LANGUAGE;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.OVERVIEW;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.POSTER;
import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsTV.VOTE_AVERAGE;

public class DetailTVShowActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TV = "extra_tv";
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private TextView tvDetailTitle, tvReleaseDate, tvRating, tvLanguage, tvOverview;
    private ImageView imgPoster, imgBg;
    private ImageButton btnFavTV, btnDelFavTV;
    private boolean isFavourited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.detail_tv_show);

        TVShowItem tvShowItem = getIntent().getParcelableExtra(EXTRA_TV);
        // String idFavTV = Integer.toString(tvShowItem.getId());

        scrollView = findViewById(R.id.scroll_view_container_tvs);
        progressBar = findViewById(R.id.progressBar_detail_tv);
        tvDetailTitle = findViewById(R.id.tv_detail_title_tvs);
        tvReleaseDate = findViewById(R.id.tv_release_date_detail_tvs);
        tvRating = findViewById(R.id.tv_rating_detail_tvs);
        tvLanguage = findViewById(R.id.tv_language_detail_tvs);
        tvOverview = findViewById(R.id.tv_overview_tvs);
        imgPoster = findViewById(R.id.img_poster_path_tvs);
        imgBg = findViewById(R.id.img_bg_tvs);

        btnFavTV = findViewById(R.id.btn_fav_tv);
        btnDelFavTV = findViewById(R.id.btn_del_fav_tv);
        btnFavTV.setOnClickListener(this);
        btnDelFavTV.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);
        btnFavTV.setVisibility(View.VISIBLE);

        if (isFavourited) {
            btnFavTV.setVisibility(View.VISIBLE);
            btnDelFavTV.setVisibility(View.GONE);
        } else {
            btnFavTV.setVisibility(View.GONE);
            btnDelFavTV.setVisibility(View.VISIBLE);
        }

        loadFavTV();
        getTV();
    }

    private void getTV() {
        TVShowItem tvShowItem = getIntent().getParcelableExtra(EXTRA_TV);

        String rating = Double.toString(tvShowItem.getVoteAverage());
        tvDetailTitle.setText(tvShowItem.getName());
        tvReleaseDate.setText(tvShowItem.getFirstAirDate());
        tvRating.setText(rating);
        tvLanguage.setText(tvShowItem.getOriginalLanguage());
        tvOverview.setText(tvShowItem.getOverview());
        Glide.with(DetailTVShowActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + tvShowItem.getPosterPath())
                .apply(new RequestOptions().transform(new RoundedCorners(12)))
                .into(imgPoster);
        Glide.with(DetailTVShowActivity.this)
                .load("https://image.tmdb.org/t/p/w185" + tvShowItem.getBackdropPath())
                .into(imgBg);
        scrollView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        progressBar.setVisibility(View.GONE);
    }

    private void loadFavTV() {
        TVShowItem tvShowItem = getIntent().getParcelableExtra(EXTRA_TV);
        TVHelper tvHelper = TVHelper.getInstance(getApplicationContext());
        tvHelper.open();
        Cursor cursor = getContentResolver().query(Uri.parse(
                CONTENT_URI_TV + "/" + tvShowItem.getId()),
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
            btnFavTV.setVisibility(View.GONE);
            btnDelFavTV.setVisibility(View.VISIBLE);
        } else {
            btnFavTV.setVisibility(View.VISIBLE);
            btnDelFavTV.setVisibility(View.GONE);
        }
    }

    private void addFavourite() {
        TVShowItem tvShowItem = getIntent().getParcelableExtra(EXTRA_TV);
        ContentValues values = new ContentValues();

        values.put(NAME, tvShowItem.getName());
        values.put(FIRST_AIR_DATE, tvShowItem.getFirstAirDate());
        values.put(VOTE_AVERAGE, tvShowItem.getVoteAverage());
        values.put(ORIGINAL_LANGUAGE, tvShowItem.getOriginalLanguage());
        values.put(OVERVIEW, tvShowItem.getOverview());
        values.put(POSTER, tvShowItem.getPosterPath());
        values.put(BACKDROP_PATH, tvShowItem.getBackdropPath());
        getContentResolver().insert(CONTENT_URI_TV, values);
    }

    private void removeFavourite() {
        TVShowItem tvShowItem = getIntent().getParcelableExtra(EXTRA_TV);
        getContentResolver().delete(Uri.parse(
                CONTENT_URI_TV + "/" + tvShowItem.getId()),
                null,
                null
        );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_fav_tv) {
            addFavourite();
            btnFavTV.setVisibility(View.GONE);
            btnDelFavTV.setVisibility(View.VISIBLE);
            Snackbar.make(view, R.string.add_favourite, Snackbar.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_del_fav_tv) {
            removeFavourite();
            btnFavTV.setVisibility(View.VISIBLE);
            btnDelFavTV.setVisibility(View.GONE);
            Snackbar.make(view, R.string.remove_favourite, Snackbar.LENGTH_SHORT).show();
        }
    }
}

