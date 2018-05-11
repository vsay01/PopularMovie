package com.udacity.popularmovie.data.network;

import com.udacity.popularmovie.data.database.MovieResponse;

public class ApiResponse {
    private MovieResponse movieResponses;
    private Throwable error;

    public ApiResponse(MovieResponse movies) {
        this.movieResponses = movies;
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
}