package com.udacity.popularmovie.ui.movie_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.MovieRepositoryImp;
import com.udacity.popularmovie.data.database.MovieContract;
import com.udacity.popularmovie.data.network.ApiResponse;

public class MovieDetailViewModel extends AndroidViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private MovieRepositoryImp mMovieRepository;
    private MediatorLiveData<Integer> mFavouriteDrawableId;

    // No argument constructor
    public MovieDetailViewModel(Application application) {
        super(application);
        mApiResponse = new MediatorLiveData<>();
        mMovieRepository = new MovieRepositoryImp();
        mFavouriteDrawableId = new MediatorLiveData<>();
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

    public boolean isMovieAlreadyFavourite(long movieId) {
        Cursor cursor = getApplication()
                .getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null, null);
        if (cursor != null && cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void removeMovieFavourite(long movieId) {
        int numRowDeleted = getApplication()
                .getContentResolver()
                .delete(MovieContract.MovieEntry.CONTENT_URI, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null);
        if (numRowDeleted <= 0) {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_selected_24dp);
        } else {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_border_default_24dp);
            Toast.makeText(getApplication(), "Remove From Favourite", Toast.LENGTH_SHORT).show();
        }
    }

    public void addMovieFavourite(long movieId, String movieTitle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, DatabaseUtils.sqlEscapeString(movieTitle));
        Uri uri = getApplication().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_selected_24dp);
            Toast.makeText(getApplication(), "Added To Favourite", Toast.LENGTH_SHORT).show();
        } else {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_border_default_24dp);
        }
    }

    public MediatorLiveData<Integer> getFavouriteDrawableId() {
        return mFavouriteDrawableId;
    }
}