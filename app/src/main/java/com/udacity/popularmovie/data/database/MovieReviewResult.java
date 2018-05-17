package com.udacity.popularmovie.data.database;

import com.squareup.moshi.Json;

public class MovieReviewResult {
    @Json(name = "author")
    public String author;
    @Json(name = "content")
    public String content;
    @Json(name = "id")
    public String id;
    @Json(name = "url")
    public String url;
}