package com.udacity.popularmovie.data.network;

import com.udacity.popularmovie.data.database.MovieResponse;
import com.udacity.popularmovie.data.database.MovieReviewResponse;
import com.udacity.popularmovie.data.database.MovieTrailerResponse;

public class ApiResponse {
    private MovieResponse movieResponses;
    private MovieTrailerResponse movieTrailerResponse;
    private MovieReviewResponse movieReviewResponse;
    private Throwable error;

    public ApiResponse(MovieResponse movies) {
        this.movieResponses = movies;
        this.error = null;
    }

    public ApiResponse(MovieTrailerResponse movies) {
        this.movieTrailerResponse = movies;
        this.error = null;
    }

    public ApiResponse(MovieReviewResponse movies) {
        this.movieReviewResponse = movies;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.movieResponses = null;
    }

    public Throwable getError() {
        return error;
    }

    public MovieResponse getMovieResponses() {
        return movieResponses;
    }

    public MovieReviewResponse getMovieReviewResponse() {
        return movieReviewResponse;
    }

    public MovieTrailerResponse getMovieTrailerResponse() {
        return movieTrailerResponse;
    }
}