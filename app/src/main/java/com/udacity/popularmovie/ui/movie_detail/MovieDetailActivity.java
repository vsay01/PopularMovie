package com.udacity.popularmovie.ui.movie_detail;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.ui.movie_landing.MovieListActivity;
import com.udacity.popularmovie.util.ColorUtils;

/**
 * An activity representing a single MovieResponse detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailActivity extends AppCompatActivity {

    private int mPaletteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getWindow().setAllowEnterTransitionOverlap(false);

        if (getIntent() != null) {
            mPaletteColor = getIntent().getIntExtra(MovieDetailFragment.ARG_ITEM_COLOR_PALETTE, ContextCompat.getColor(this, R.color.movieDetailTitleBg));
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //set color action bar
            actionBar.setBackgroundDrawable(new ColorDrawable(ColorUtils.manipulateColor(mPaletteColor, 0.62f)));

            //set color status bar
            getWindow().setStatusBarColor(ColorUtils.manipulateColor(mPaletteColor, 0.32f));
        }

        if (savedInstanceState == null && getIntent() != null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID,
                    getIntent().getParcelableExtra(MovieDetailFragment.ARG_ITEM_ID));
            arguments.putString(MovieDetailFragment.ARG_ITEM_TRANSITION_ID,
                    getIntent().getStringExtra(MovieDetailFragment.ARG_ITEM_TRANSITION_ID));
            arguments.putInt(MovieDetailFragment.ARG_ITEM_COLOR_PALETTE, mPaletteColor);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}