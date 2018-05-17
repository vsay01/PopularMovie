package com.udacity.popularmovie.data.network;

import com.udacity.popularmovie.data.database.MovieResponse;
import com.udacity.popularmovie.data.database.MovieReviewResponse;
import com.udacity.popularmovie.data.database.MovieTrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieResponse> getMostPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getHighestRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movieId}/videos")
    Call<MovieTrailerResponse> getMovieTrailers(@Path("movieId") long movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/reviews")
    Call<MovieReviewResponse> getMovieReviews(@Path("movieId") long movieId, @Query("api_key") String apiKey);
}