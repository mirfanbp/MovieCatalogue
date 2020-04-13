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
import com.fando.picodiploma.moviecatalogue_4.activity.DetailTVShowActivity;
import com.fando.picodiploma.moviecatalogue_4.model.TVShowItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    Context context;
    private ArrayList<TVShowItem> tvData = new ArrayList<>();

    public TVShowAdapter(Context context) {
        this.context = context;
    }

    public void refill(ArrayList<TVShowItem> items) {
        this.tvData = new ArrayList<>();
        this.tvData.addAll(items);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<TVShowItem> items) {
        tvData.clear();
        tvData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv, viewGroup, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bind(tvData.get(position));
        holder.cardView.setOnClickListener(view -> {
            // Toast.makeText(context, "You choose " + tvData.get(position).getName(), Toast.LENGTH_SHORT).show();
            Intent detailTVIntent = new Intent(context, DetailTVShowActivity.class);
            detailTVIntent.putExtra(DetailTVShowActivity.EXTRA_TV, tvData.get(position));
            context.startActivity(detailTVIntent);
        });
    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName, tvRating, tvReleaseDate;
        ImageView imgPoster;

        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view_tv);
            tvName = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            imgPoster = itemView.findViewById(R.id.img_item_poster_tv);
        }

        void bind(TVShowItem tv) {
            Log.d("DATA_TV", tv.toString());
            String rating = Double.toString(tv.getVoteAverage());

            tvName.setText(tv.getName());
            tvReleaseDate.setText(tv.getFirstAirDate());
            tvRating.setText(rating);
            Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w185" + tv.getPosterPath()).into(imgPoster);
            cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        }
    }
}

