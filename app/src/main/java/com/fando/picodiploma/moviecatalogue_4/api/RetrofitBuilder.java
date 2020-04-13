package com.fando.picodiploma.moviecatalogue_4.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    static Retrofit retrovit;
    static String BASE_URL = "https://api.themoviedb.org/3/";

    // bikin objeck baru dari retrofit
    public static Retrofit getInstance() {
        if (retrovit == null) {
            retrovit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrovit;
    }
}

