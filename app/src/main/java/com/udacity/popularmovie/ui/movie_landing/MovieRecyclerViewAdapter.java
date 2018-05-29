package com.udacity.popularmovie.ui.movie_landing;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.udacity.popularmovie.R;
import com.udacity.popularmovie.base.GlideApp;
import com.udacity.popularmovie.data.models.MovieResult;
import com.udacity.popularmovie.data.network.Config;
import com.udacity.popularmovie.util.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final MovieListActivity mParentActivity;
    private final List<MovieResult> mMovieList;
    private final MovieClickListener mMovieClickListener;

    MovieRecyclerViewAdapter(MovieListActivity parent,
                             List<MovieResult> items,
                             MovieClickListener movieClickListener) {
        mMovieList = items;
        mParentActivity = parent;
        mMovieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        GlideApp.with(mParentActivity)
                .asBitmap()
                .load(Config.BASE_IMAGE_URL + mMovieList.get(position).posterPath)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        mParentActivity.startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        mParentActivity.startPostponedEnterTransition();
                        if (resource != null) {
                            String saveImagePath = ImageUtils.saveImage(resource, mMovieList.get(position).id);
                            if(saveImagePath != null){
                                //save image path and movie id to sqlite
                            }
                            Palette p = Palette.from(resource).generate();
                            // Use generated instance
                            holder.mColorPalette = p.getMutedColor(ContextCompat.getColor(mParentActivity, R.color.movieDetailTitleBg));
                        }
                        return false;
                    }
                })
                .into(holder.mImageView);

        ViewCompat.setTransitionName(holder.mImageView, mMovieList.get(position).title);

        holder.mImageView.setOnClickListener(view -> mMovieClickListener.onMovieClicked(mMovieList.get(position), holder.mColorPalette, holder.mImageView));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_image)
        AppCompatImageView mImageView;

        int mColorPalette;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}