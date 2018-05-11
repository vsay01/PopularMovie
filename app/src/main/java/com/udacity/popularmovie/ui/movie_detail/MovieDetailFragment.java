package com.udacity.popularmovie.ui.movie_detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.database.MovieResult;
import com.udacity.popularmovie.databinding.MovieDetailBinding;
import com.udacity.popularmovie.ui.movie_landing.MovieListActivity;
import com.udacity.popularmovie.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single MovieResponse detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_TRANSITION_ID = "item_transition_id";
    public static final String ARG_ITEM_COLOR_PALETTE = "movie_color_palette";

    /**
     * The dummy content this fragment is presenting.
     */
    private MovieResult mItem;
    private String mTransitionName;
    private int mColorPalette;


    @BindView(R.id.background_view)
    View mViewBackground;

    @BindView(R.id.iv_movie_poster)
    AppCompatImageView mMoviePoster;

    @BindView(R.id.tv_release_date)
    AppCompatTextView mTvReleaseDate;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getParcelable(ARG_ITEM_ID);
            mTransitionName = getArguments().getString(ARG_ITEM_TRANSITION_ID);
            mColorPalette = getArguments().getInt(ARG_ITEM_COLOR_PALETTE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MovieDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.movie_detail, container, false);
        binding.setMovie(mItem);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        mTvReleaseDate.setText(DateUtils.formattedDateFromString(mItem.releaseDate));
        mMoviePoster.setTransitionName(mTransitionName);
        if ((mColorPalette != 0)) {
            mViewBackground.setBackgroundColor(mColorPalette);
        } else {
            if (getActivity() != null) {
                mViewBackground.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.movieDetailTitleBg));
            }
        }
        return view;
    }
}