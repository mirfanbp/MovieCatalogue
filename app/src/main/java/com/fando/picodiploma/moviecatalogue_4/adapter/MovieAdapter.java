package com.fando.picodiploma.moviecatalogue_4.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.activity.DetailMovieActivity;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MoviesItem> moviesData = new ArrayList<>();
    Context context;

    public MovieAdapter(Context context) {
        this.context = context;

    }

    public void refill(ArrayList<MoviesItem> items) {
        this.moviesData = new ArrayList<>();
        this.moviesData.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<MoviesItem> items) {
        moviesData.clear();
        moviesData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(moviesData.get(position));

        holder.cardView.setOnClickListener(view -> {
            // Toast.makeText(context, "You choose " + moviesData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent detailMovieIntent = new Intent(context, DetailMovieActivity.class);
            detailMovieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, moviesData.get(position));
            context.startActivity(detailMovieIntent);
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle, tvRating, tvReleaseDate;
        ImageView imgPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view_movie);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            tvRating = itemView.findViewById(R.id.tv_rating);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }

        void bind(MoviesItem movies) {
            Log.d("DATA_MOVIE", movies.toString());
            String rating = Double.toString(movies.getVoteAverage());

            tvTitle.setText(movies.getTitle());
            tvReleaseDate.setText(movies.getReleaseDate());
            tvRating.setText(rating);
            Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w185" + movies.getPosterPath()).into(imgPoster);
            cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        }
    }
}

