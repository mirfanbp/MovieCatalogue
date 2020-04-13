package com.fando.picodiploma.moviecatalogue_4.callback;

import com.fando.picodiploma.moviecatalogue_4.model.MoviesItem;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(ArrayList<MoviesItem> movies);
}
