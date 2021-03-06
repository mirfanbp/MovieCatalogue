package com.fando.picodiploma.favmovie.fragment;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fando.picodiploma.favmovie.R;
import com.fando.picodiploma.favmovie.adapter.FavTVAdapter;
import com.fando.picodiploma.favmovie.db.TVHelper;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.fando.picodiploma.favmovie.db.DatabaseContract.FavouriteColumnsTV.CONTENT_URI_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTVFragment extends Fragment {
    private static String LIST_STATE = "list_state";
    private RecyclerView rvFavTV;
    private FavTVAdapter favTVAdapter;
    private Cursor cursorTV;

    public FavTVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavTV = view.findViewById(R.id.rv_fav_tv);
        if (Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getActivity() != null) {
                rvFavTV.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
            }
        } else {
            rvFavTV.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        }
        rvFavTV.setHasFixedSize(true);

        TVHelper tvHelper = TVHelper.getInstance(getContext());
        tvHelper.open();
        new getFavTV().execute();

        favTVAdapter = new FavTVAdapter(getActivity());
        favTVAdapter.setTvShowFavData(cursorTV);
        rvFavTV.setAdapter(favTVAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new getFavTV().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class getFavTV extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return Objects.requireNonNull(getContext())
                    .getContentResolver().query(
                            CONTENT_URI_TV,
                            null,
                            null,
                            null,
                            null
                    );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            cursorTV = cursor;
            favTVAdapter.setTvShowFavData(cursorTV);
            favTVAdapter.notifyDataSetChanged();
        }
    }

}
