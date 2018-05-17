package com.udacity.popularmovie.data.database;

import com.squareup.moshi.Json;

public class MovieTrailerResult {
    @Json(name = "id")
    public String id;
    @Json(name = "iso_639_1")
    public String iso_639_1;
    @Json(name = "iso_3166_1")
    public String iso_3166_1;
    @Json(name = "key")
    public String key;
    @Json(name = "name")
    public String name;
    @Json(name = "site")
    public String site;
    @Json(name = "size")
    public String size;
    @Json(name = "type")
    public String type;
}
