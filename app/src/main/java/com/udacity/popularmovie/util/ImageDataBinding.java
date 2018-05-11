package com.udacity.popularmovie.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.popularmovie.base.GlideApp;
import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.network.Config;

public class ImageDataBinding {
    @BindingAdapter({"imageURL"})
    public static void loadImage(AppCompatImageView view, String imageUrl) {
        GlideApp.with(view.getContext())
                .load(Config.BASE_IMAGE_URL + imageUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }
}