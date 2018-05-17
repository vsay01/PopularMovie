package com.udacity.popularmovie.ui.movie_detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.database.MovieReviewResult;
import com.udacity.popularmovie.databinding.MovieReviewItemBinding;

import java.util.List;

public class MovieReviewRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieReviewRecyclerViewAdapter.ViewHolder> {

    private final List<MovieReviewResult> mMovieReviewList;

    MovieReviewRecyclerViewAdapter(List<MovieReviewResult> items) {
        mMovieReviewList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieReviewItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_review_item, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.movieReviewItemBinding.setMovieResult(mMovieReviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieReviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MovieReviewItemBinding movieReviewItemBinding;

        ViewHolder(MovieReviewItemBinding movieReviewItemBinding1) {
            super(movieReviewItemBinding1.getRoot());
            this.movieReviewItemBinding = movieReviewItemBinding1;
        }
    }
}