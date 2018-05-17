package com.udacity.popularmovie.ui.movie_detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.udacity.popularmovie.data.MovieRepositoryImp;
import com.udacity.popularmovie.data.network.ApiResponse;

public class MovieDetailViewModel extends ViewModel {
    private MediatorLiveData<ApiResponse> mApiResponse;
    private MovieRepositoryImp mMovieRepository;

    // No argument constructor
    public MovieDetailViewModel() {
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
}