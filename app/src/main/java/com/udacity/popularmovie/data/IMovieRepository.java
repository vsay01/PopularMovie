package com.udacity.popularmovie.data;

import android.arch.lifecycle.LiveData;

import com.udacity.popularmovie.data.network.ApiResponse;

public interface IMovieRepository {
    LiveData<ApiResponse> getMostPopularMovies();
    LiveData<ApiResponse> getHighestRatedMovies();
    LiveData<ApiResponse> getMovieTrailer(long movieId);
    LiveData<ApiResponse> getMovieReview(long movieId);
}