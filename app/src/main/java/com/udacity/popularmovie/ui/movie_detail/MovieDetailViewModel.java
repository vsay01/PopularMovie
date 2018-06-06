package com.udacity.popularmovie.ui.movie_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.ContentValues;
import android.database.Cursor;
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
                .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId + " AND " + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE + " = " + 1, null, null);
        if (cursor != null && cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void removeMovieFavourite(long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE, 0);
        int updatedRow = getApplication().getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, contentValues, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null);
        if (updatedRow > 0) {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_border_default_24dp);
            Toast.makeText(getApplication(), R.string.text_remove_favorite, Toast.LENGTH_SHORT).show();
        } else {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_selected_24dp);
        }
    }

    public void addMovieFavourite(long movieId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE, 1);
        int updatedRow = getApplication().getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, contentValues, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null);
        if (updatedRow > 0) {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_selected_24dp);
            Toast.makeText(getApplication(), R.string.text_add_favorite, Toast.LENGTH_SHORT).show();
        } else {
            mFavouriteDrawableId.postValue(R.drawable.ic_favorite_border_default_24dp);
        }
    }

    public MediatorLiveData<Integer> getFavouriteDrawableId() {
        return mFavouriteDrawableId;
    }
}