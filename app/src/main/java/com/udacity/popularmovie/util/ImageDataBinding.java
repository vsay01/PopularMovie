package com.udacity.popularmovie.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.popularmovie.R;
import com.udacity.popularmovie.base.GlideApp;
import com.udacity.popularmovie.data.network.Config;

import java.io.File;

public class ImageDataBinding {
    @BindingAdapter({"imageURL"})
    public static void loadImageMovie(AppCompatImageView view, String imageUrl) {
        if (imageUrl.startsWith("/data/")) {
            GlideApp.with(view.getContext())
                    .load(new File(imageUrl))
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(view);
        } else {
            GlideApp.with(view.getContext())
                    .load(Config.BASE_IMAGE_URL + imageUrl)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(view);
        }
    }

    @BindingAdapter({"imageURLMovieTrailer"})
    public static void loadMovieTrailer(AppCompatImageView view, String videoKey) {
        GlideApp.with(view.getContext())
                .load(getYoutubeThumbnailUrlFromVideoUrl(videoKey))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }

    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoKey) {
        String imgUrl = "http://img.youtube.com/vi/" + videoKey + "/0.jpg";
        return imgUrl;
    }
}