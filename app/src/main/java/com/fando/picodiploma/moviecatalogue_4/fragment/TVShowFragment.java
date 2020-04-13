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
import com.fando.picodiploma.moviecatalogue_4.adapter.TVShowAdapter;
import com.fando.picodiploma.moviecatalogue_4.api.ApiService;
import com.fando.picodiploma.moviecatalogue_4.api.RetrofitBuilder;
import com.fando.picodiploma.moviecatalogue_4.model.TVShowItem;
import com.fando.picodiploma.moviecatalogue_4.model.TVShowResponse;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowFragment extends Fragment {
    private TVShowAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvTV;
    private ArrayList<TVShowItem> tv = new ArrayList<>();
    private String TAG = "TVShowFragmentTag";
    private static String LIST_STATE = "list_state";

    private static final String language = "en-US";

    public TVShowFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progressBar_tv);
        showLoading(true);

        rvTV = view.findViewById(R.id.rv_tv);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvTV.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        } else {
            rvTV.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        }

        rvTV.setHasFixedSize(true);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            tv = savedInstanceState.getParcelableArrayList(LIST_STATE);
            // savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            adapter = new TVShowAdapter(getActivity());
            adapter.refill(tv);
            showLoading(false);
            rvTV.setAdapter(adapter);
        } else {
            loadData("");
        }
    }

    private void loadData(final String keyword) {
        ApiService api = RetrofitBuilder.getInstance().create(ApiService.class);
        Call<TVShowResponse> post;
        if (keyword.length() > 0) {
            post = api.getSearchTV(BuildConfig.API_KEY, keyword);
        } else {
            post = api.getTV(BuildConfig.API_KEY, language);
        }
        post.enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                // Toast.makeText(getActivity(), response.body().toString(),Toast.LENGTH_LONG).show();
                // loading dismiss
                tv = response.body().getResults();
                adapter = new TVShowAdapter(getActivity());
                adapter.setData(tv);

                showLoading(false);

                rvTV.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.loading, Toast.LENGTH_SHORT).show();
                showLoading(false);
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(LIST_STATE, tv);
        // Selalu simpan pemanggil superclass di bawah agar data di view tetap tersimpan
        super.onSaveInstanceState(savedInstanceState);
        // savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, rvTV.getLayoutManager().onSaveInstanceState());
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search_tv));
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
