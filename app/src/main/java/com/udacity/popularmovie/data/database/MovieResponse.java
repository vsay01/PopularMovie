package com.udacity.popularmovie.data.database;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieResponse {
    @Json(name = "page")
    public Integer page;
    @Json(name = "total_results")
    public Integer totalResults;
    @Json(name = "total_pages")
    public Integer totalPages;
    @Json(name = "results")
    public List<MovieResult> results = null;
}