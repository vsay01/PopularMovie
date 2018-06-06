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

import java.util.ArrayList;
import java.util.List;

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

    public List<MovieResult> loadMoviesOfflineSQL(int sortType) {
        List<MovieResult> movieResultList = new ArrayList<>();
        Cursor cursor;
        if (sortType == 0) {
            cursor = getApplication()
                    .getContentResolver()
                    .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_POPULAR + " = " + 1, null, null);
        } else if (sortType == 1) {
            cursor = getApplication()
                    .getContentResolver()
                    .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_HIGHEST_RATED + " = " + 1, null, null);
        } else {
            cursor = getApplication()
                    .getContentResolver()
                    .query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE + " = " + 1, null, null);
        }
        while (cursor != null && cursor.moveToNext()) {
            MovieResult movieResult = populateMovieResult(cursor);
            if (movieResult != null) {
                movieResultList.add(movieResult);
            }
        }
        return movieResultList;
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
            if (movieResults[0] != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieResults[0].id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movieResults[0].title);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, movieResults[0].savedImagePath);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RATING, String.valueOf(movieResults[0].voteAverage));
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movieResults[0].releaseDate);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_POPULAR, (movieResults[0].isPopular) ? 1 : 0);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_HIGHEST_RATED, (movieResults[0].isHighestRated) ? 1 : 0);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE, (movieResults[0].isFavorited) ? 1 : 0);
                contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS, movieResults[0].overview);
                if (!isMovieAlreadySaved(movieResults[0].id)) {
                    getApplication().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
                } else {
                    getApplication().getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, contentValues, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieResults[0].id, null);
                }
            }
            return null;
        }
    }

    MovieResult populateMovieResult(Cursor cursor) {
        try {
            int idIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
            int titleIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);
            int posterIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER);
            int ratingIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_RATING);
            int releaseDateIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
            int isPopularIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_POPULAR);
            int isHighestRatedIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_HIGHEST_RATED);
            int isFavoriteIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE);
            int synopsisIndex = cursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS);

            long id = cursor.getLong(idIndex);
            String title = cursor.getString(titleIndex);
            String poster = cursor.getString(posterIndex);
            String rating = cursor.getString(ratingIndex);
            String releaseDate = cursor.getString(releaseDateIndex);
            int isPopular = cursor.getInt(isPopularIndex);
            int isHighestRated = cursor.getInt(isHighestRatedIndex);
            int isFavorite = cursor.getInt(isFavoriteIndex);
            String synopsis = cursor.getString(synopsisIndex);

            return new MovieResult(id, title, poster,
                    Double.parseDouble(rating), releaseDate, isPopular == 1,
                    isHighestRated == 1, isFavorite == 1, synopsis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}