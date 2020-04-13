package com.fando.picodiploma.moviecatalogue_4.callback;

import com.fando.picodiploma.moviecatalogue_4.model.TVShowItem;

import java.util.ArrayList;

public interface LoadTVCallback {
    void preExecute();

    void postExecute(ArrayList<TVShowItem> tv);
}
