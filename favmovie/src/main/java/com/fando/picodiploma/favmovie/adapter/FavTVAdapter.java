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
import com.fando.picodiploma.favmovie.model.TVShowItem;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FavTVAdapter extends RecyclerView.Adapter<FavTVAdapter.FavTVHolder> {
    private final Context context;
    private Cursor tvCursor;

    public FavTVAdapter(Context context) {
        this.context = context;
    }

    public void setTvShowFavData(Cursor tvShowFavData) {
        this.tvCursor = tvShowFavData;
    }

    private TVShowItem getItem(int position) {
        if (!tvCursor.moveToPosition(position))
            throw new IllegalStateException("Position Error");
        return new TVShowItem(tvCursor);
    }


    @NonNull
    @Override
    public FavTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv, parent, false);
        return new FavTVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTVHolder holder, int position) {
        final TVShowItem tv = getItem(position);
        Log.d("DATA_FAVOURITE_TV_SHOW", tv.toString());

        String rating = Double.toString(tv.getVoteAverage());

        holder.tvName.setText(tv.getName());
        holder.tvRating.setText(rating);
        holder.tvReleaseDate.setText(tv.getFirstAirDate());
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + tv.getPosterPath()).into(holder.imgPoster);
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
    }

    @Override
    public int getItemCount() {
        if (tvCursor == null) return 0;
        return tvCursor.getCount();
    }

    class FavTVHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName, tvRating, tvReleaseDate;
        ImageView imgPoster;

        FavTVHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_tv);
            tvName = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            imgPoster = itemView.findViewById(R.id.img_item_poster_tv);
        }
    }
}
