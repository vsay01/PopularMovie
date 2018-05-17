package com.udacity.popularmovie.data.database;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieTrailerResponse {
    @Json(name = "results")
    public List<MovieTrailerResult> movieTrailerResultList;
}
