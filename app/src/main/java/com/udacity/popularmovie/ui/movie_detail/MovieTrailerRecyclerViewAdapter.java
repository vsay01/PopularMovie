package com.udacity.popularmovie.ui.movie_detail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.models.MovieTrailerResult;
import com.udacity.popularmovie.databinding.MovieTrailerItemBinding;

import java.util.List;

public class MovieTrailerRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.ViewHolder> {

    private final List<MovieTrailerResult> mMovieList;
    private MovieTrailerHandler mMovieTrailerHandler;
    private Context mContext;

    MovieTrailerRecyclerViewAdapter(Context context, List<MovieTrailerResult> items) {
        mMovieList = items;
        this.mContext = context;
        this.mMovieTrailerHandler = new MovieTrailerHandler(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieTrailerItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_trailer_item, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.movieTrailerItemBinding.setMovieTrailer(mMovieList.get(position));
        holder.movieTrailerItemBinding.setMovieTrailerHandler(mMovieTrailerHandler);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MovieTrailerItemBinding movieTrailerItemBinding;

        ViewHolder(MovieTrailerItemBinding movieTrailerItemBinding) {
            super(movieTrailerItemBinding.getRoot());
            this.movieTrailerItemBinding = movieTrailerItemBinding;
        }
    }
}