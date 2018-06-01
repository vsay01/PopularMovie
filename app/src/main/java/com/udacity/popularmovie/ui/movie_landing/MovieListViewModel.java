package com.udacity.popularmovie.ui.movie_landing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.udacity.popularmovie.data.MovieRepositoryImp;
import com.udacity.popularmovie.data.database.MovieContract;
import com.udacity.popularmovie.data.models.MovieResult;
import com.udacity.popularmovie.data.network.ApiResponse;

public class MovieListViewModel extends AndroidViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private MovieRepositoryImp mMovieRepository;

    // No argument constructor
    public MovieListViewModel(Application application) {
        super(application);
        mApiResponse = new MediatorLiveData<>();
        mMovieRepository = new MovieRepositoryImp();
    }

    @NonNull
    public LiveData<ApiResponse> getApiResponse() {
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadMostPopularMovies() {
        mApiResponse.addSource(
                mMovieRepository.getMostPopularMovies(),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadHighestRatedMovies() {
        mApiResponse.addSource(
                mMovieRepository.getHighestRatedMovies(),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );
        return mApiResponse;
    }

    public boolean isMovieAlreadySaved(long movieId) {
        Cursor cursor = getApplication()
                .getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null, null);
        if (cursor != null && cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    public void saveMovie(MovieResult movieResult) {
        new MovieOperationAysnc().execute(movieResult);
    }

    public class MovieOperationAysnc extends AsyncTask<MovieResult, Void, Void> {

        @Override
        protected Void doInBackground(MovieResult... movieResults) {
            if (movieResults[0] != null && !isMovieAlreadySaved(movieResults[0].id)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieResults[0].id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movieResults[0].title);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, movieResults[0].savedImagePath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING, String.valueOf(movieResults[0].voteAverage));
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movieResults[0].releaseDate);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS, movieResults[0].overview);
                getApplication().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
            }
            return null;
        }
    }
}