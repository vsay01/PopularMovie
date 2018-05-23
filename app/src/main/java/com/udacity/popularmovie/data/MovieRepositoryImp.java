package com.udacity.popularmovie.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.udacity.popularmovie.data.models.MovieResponse;
import com.udacity.popularmovie.data.models.MovieReviewResponse;
import com.udacity.popularmovie.data.models.MovieTrailerResponse;
import com.udacity.popularmovie.data.network.ApiResponse;
import com.udacity.popularmovie.data.network.Config;
import com.udacity.popularmovie.data.network.MovieApiService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieRepositoryImp implements IMovieRepository {
    private MovieApiService mApiService;

    public MovieRepositoryImp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(Config.BASE_URL)
                .build();
        mApiService = retrofit.create(MovieApiService.class);
    }

    @Override
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

    @Override
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

    @Override
    public LiveData<ApiResponse> getMovieTrailer(long movieId) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<MovieTrailerResponse> call = mApiService.getMovieTrailers(movieId, Config.API_KEY);
        call.enqueue(new Callback<MovieTrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailerResponse> call, @NonNull Response<MovieTrailerResponse> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailerResponse> call, @NonNull Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<ApiResponse> getMovieReview(long movieId) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<MovieReviewResponse> call = mApiService.getMovieReviews(movieId, Config.API_KEY);
        call.enqueue(new Callback<MovieReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieReviewResponse> call, @NonNull Response<MovieReviewResponse> response) {
                liveData.setValue(new ApiResponse(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewResponse> call, @NonNull Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }
}