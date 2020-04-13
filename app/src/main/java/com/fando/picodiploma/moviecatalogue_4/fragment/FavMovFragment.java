package com.fando.picodiploma.moviecatalogue_4.fragment;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.adapter.FavMovAdapter;
import com.fando.picodiploma.moviecatalogue_4.db.MovieHelper;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.fando.picodiploma.moviecatalogue_4.db.DatabaseContract.FavouriteColumnsMovie.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovFragment extends Fragment {
    private FavMovAdapter favMovAdapter;
    private ArrayList<MoviesItem> moviesFav = new ArrayList<>();
    private Cursor cursorMovie;

    public FavMovFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_mov, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFavMovie = view.findViewById(R.id.rv_fav_movie);
        if (Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getActivity() != null) {
                rvFavMovie.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
            }
        } else {
            rvFavMovie.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        }
        rvFavMovie.setHasFixedSize(true);

        MovieHelper movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        new getFavMovie().execute();

        favMovAdapter = new FavMovAdapter(getActivity());
        favMovAdapter.setMoviesFavData(cursorMovie);
        rvFavMovie.setAdapter(favMovAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        new getFavMovie().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class getFavMovie extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext())
                    .getContentResolver().query(
                            CONTENT_URI,
                            null,
                            null,
                            null,
                            null
                    );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            cursorMovie = cursor;
            favMovAdapter.setMoviesFavData(cursorMovie);
            favMovAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search favourite movie...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String keyword = newText.toLowerCase();
                ArrayList<MoviesItem> favMovieList = new ArrayList<>();

                for (MoviesItem favourite : moviesFav) {
                    String title = favourite.getTitle().toLowerCase();
                    if (title.contains(keyword)) {
                        favMovieList.add(favourite);
                    }
                }
                favMovAdapter.searchFavourite(favMovieList);
                return true;
            }
        });
    }
}
