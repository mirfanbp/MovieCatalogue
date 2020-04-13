package com.fando.picodiploma.favmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fando.picodiploma.favmovie.R;
import com.fando.picodiploma.favmovie.model.MoviesItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavMovAdapter extends RecyclerView.Adapter<FavMovAdapter.FavMovHolder> {
    private final ArrayList<MoviesItem> moviesFavData = new ArrayList<>();
    private final Context context;
    private Cursor movieCursor;

    // activity/context
    public FavMovAdapter(Context context) {
        this.context = context;
    }

    public void setMoviesFavData(Cursor moviesFavData) {
        this.movieCursor = moviesFavData;
    }

    private MoviesItem getItem(int position) {
        if (!movieCursor.moveToPosition(position))
            throw new IllegalStateException("Position error");
        return new MoviesItem(movieCursor);
    }

    @NonNull
    @Override
    public FavMovHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavMovHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovHolder holder, int position) {
        final MoviesItem movies = getItem(position);
        Log.d("DATA_FAVOURITE_MOVIE", movies.toString());

        String rating = Double.toString(movies.getVoteAverage());
        holder.tvTitle.setText(movies.getTitle());
        holder.tvReleaseDate.setText(movies.getReleaseDate());
        holder.tvRating.setText(rating);
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + movies.getPosterPath()).into(holder.imgPoster);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
    }

    @Override
    public int getItemCount() {
        if (movieCursor == null) return 0;
        return movieCursor.getCount();
    }

    class FavMovHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvRating, tvReleaseDate;
        ImageView imgPoster;

        FavMovHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            tvRating = itemView.findViewById(R.id.tv_rating);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
