package com.udacity.popularmovie.ui.movie_landing;

import android.support.v7.widget.AppCompatImageView;

import com.udacity.popularmovie.data.models.MovieResult;

public interface MovieClickListener {
    void onMovieClicked(MovieResult movieResult, int colorPalette, AppCompatImageView moviePoster);
}