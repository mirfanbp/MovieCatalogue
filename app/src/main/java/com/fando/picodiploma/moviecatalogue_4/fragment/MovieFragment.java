package com.fando.picodiploma.moviecatalogue_4.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.fando.picodiploma.moviecatalogue_4.BuildConfig;
import com.fando.picodiploma.moviecatalogue_4.R;
import com.fando.picodiploma.moviecatalogue_4.adapter.MovieAdapter;
import com.fando.picodiploma.moviecatalogue_4.api.ApiService;
import com.fando.picodiploma.moviecatalogue_4.api.RetrofitBuilder;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;
import com.fando.picodiploma.moviecatalogue_4.model.MoviesResponse;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private ArrayList<MoviesItem> movies = new ArrayList<>();
    private String TAG = "MovieFragmentTag";
    private static String LIST_STATE = "list_state";

    private static final String language = "en-US";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progressBar);
        showLoading(true);

        rvMovie = view.findViewById(R.id.rv_movie);
        if (Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvMovie.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        } else {
            rvMovie.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        }

        rvMovie.setHasFixedSize(true);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(LIST_STATE);
            // savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            adapter = new MovieAdapter(getActivity());
            adapter.refill(movies);
            showLoading(false);
            rvMovie.setAdapter(adapter);
        } else {
            loadData("");
        }
    }

    private void loadData(final String keyword) {
        ApiService api = RetrofitBuilder.getInstance().create(ApiService.class);
        Call<MoviesResponse> post;
        if (keyword.length() > 0) {
            post = api.getSearchMovie(BuildConfig.API_KEY, keyword);
        } else {
            post = api.getMovie(BuildConfig.API_KEY, language);
        }
        post.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                // Toast.makeText(getActivity(), response.body().toString(),Toast.LENGTH_LONG).show();
                movies = response.body().getResults();
                adapter = new MovieAdapter(getActivity());
                adapter.setData(movies);

                showLoading(false);

                rvMovie.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();
                showLoading(false);
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(LIST_STATE, movies);
        // Selalu simpan pemanggil superclass di bawah agar data di view tetap tersimpan
        super.onSaveInstanceState(savedInstanceState);
        // savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, rvMovie.getLayoutManager().onSaveInstanceState());
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_movie));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    loadData(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadData(newText);
                return false;
            }
        });
    }
}