package com.udacity.popularmovie.data.network;

import com.udacity.popularmovie.data.database.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieResponse> getMostPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getHighestRatedMovies(@Query("api_key") String apiKey);
}