package com.udacity.popularmovie.ui.movie_landing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.udacity.popularmovie.data.MovieRepositoryImp;
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
}