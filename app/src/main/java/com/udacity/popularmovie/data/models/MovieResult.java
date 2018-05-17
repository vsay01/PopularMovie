package com.udacity.popularmovie.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class MovieResult implements Parcelable {
    @Json(name = "vote_count")
    public Integer voteCount;
    @Json(name = "id")
    public Long id;
    @Json(name = "video")
    public Boolean video;
    @Json(name = "vote_average")
    public Double voteAverage;
    @Json(name = "title")
    public String title;
    @Json(name = "popularity")
    public Double popularity;
    @Json(name = "poster_path")
    public String posterPath;
    @Json(name = "original_language")
    public String originalLanguage;
    @Json(name = "original_title")
    public String originalTitle;
    @Json(name = "genre_ids")
    public List<Integer> genreIds = null;
    @Json(name = "backdrop_path")
    public String backdropPath;
    @Json(name = "adult")
    public Boolean adult;
    @Json(name = "overview")
    public String overview;
    @Json(name = "release_date")
    public String releaseDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.voteCount);
        dest.writeValue(this.id);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.title);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeList(this.genreIds);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.adult);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }

    public MovieResult() {
    }

    protected MovieResult(Parcel in) {
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.title = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.backdropPath = in.readString();
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<MovieResult> CREATOR = new Parcelable.Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel source) {
            return new MovieResult(source);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };
}