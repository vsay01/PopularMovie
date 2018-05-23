package com.udacity.popularmovie.ui.movie_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.udacity.popularmovie.data.MovieRepositoryImp;
import com.udacity.popularmovie.data.database.MovieContract;
import com.udacity.popularmovie.data.network.ApiResponse;

public class MovieDetailViewModel extends AndroidViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private MovieRepositoryImp mMovieRepository;

    // No argument constructor
    public MovieDetailViewModel(Application application) {
        super(application);
        mApiResponse = new MediatorLiveData<>();
        mMovieRepository = new MovieRepositoryImp();
    }

    @NonNull
    public LiveData<ApiResponse> getApiResponse() {
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadMovieTrailers(long movieId) {
        mApiResponse.addSource(
                mMovieRepository.getMovieTrailer(movieId),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadMovieReviews(long movieId) {
        mApiResponse.addSource(
                mMovieRepository.getMovieReview(movieId),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );
        return mApiResponse;
    }


    public void addMovieFavourite(long movieId, String movieTitle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, DatabaseUtils.sqlEscapeString(movieTitle));
        Uri uri = getApplication().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Toast.makeText(getApplication(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }
}