package com.udacity.popularmovie.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.udacity.popularmovie.data.database.MovieResponse;
import com.udacity.popularmovie.data.network.ApiResponse;
import com.udacity.popularmovie.data.network.Config;
import com.udacity.popularmovie.data.network.MovieApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieRepositoryImp implements IMovieRepository {
    private MovieApiService mApiService;

    public MovieRepositoryImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        mApiService = retrofit.create(MovieApiService.class);
    }

    public LiveData<ApiResponse> getMostPopularMovies() {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<MovieResponse> call = mApiService.getMostPopularMovies(Config.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }

    public LiveData<ApiResponse> getHighestRatedMovies() {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<MovieResponse> call = mApiService.getHighestRatedMovies(Config.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }
}