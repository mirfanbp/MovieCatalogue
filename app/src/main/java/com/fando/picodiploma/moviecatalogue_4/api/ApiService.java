package com.fando.picodiploma.moviecatalogue_4.api;

import com.fando.picodiploma.moviecatalogue_4.model.MoviesResponse;
import com.fando.picodiploma.moviecatalogue_4.model.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie")
    Call<MoviesResponse> getMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TVShowResponse> getTV(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<MoviesResponse> getSearchMovie(
            @Query("api_key") String apiKey,
            @Query("query") String keyword
    );

    @GET("search/tv")
    Call<TVShowResponse> getSearchTV(
            @Query("api_key") String apiKey,
            @Query("query") String keyword
    );

    @GET("discover/movie")
    Call<MoviesResponse> getReleaseMovie(@Query("api_key") String apiKey,
                                         @Query("primary_release_date.gte") String primaryReleaseDate,
                                         @Query("primary_release_date.lte") String primaryReleaseDateLte
    );

//    @GET("/3/discover/tv?api_key=" + API_KEY + "&language=en-US")
//    Call<TVShowResponse> getTV();

//    @GET("/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=")
//    Call<MoviesResponse> getMovieBySearch(String keyword);
}

