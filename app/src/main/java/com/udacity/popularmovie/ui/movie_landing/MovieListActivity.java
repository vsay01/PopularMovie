package com.udacity.popularmovie.ui.movie_landing;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.udacity.popularmovie.R;
import com.udacity.popularmovie.data.database.MovieResult;
import com.udacity.popularmovie.data.network.Config;
import com.udacity.popularmovie.ui.movie_detail.MovieDetailActivity;
import com.udacity.popularmovie.ui.movie_detail.MovieDetailFragment;
import com.udacity.popularmovie.util.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.popularmovie.ui.movie_detail.MovieDetailFragment.ARG_ITEM_COLOR_PALETTE;
import static com.udacity.popularmovie.ui.movie_detail.MovieDetailFragment.ARG_ITEM_TRANSITION_ID;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private MovieListViewModel mViewModel;
    private int mSelectedItem = 0;
    private static final String SELECTED_ITEM_POSITION = "ItemPosition";

    @BindView(R.id.movie_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.no_network_message)
    AppCompatTextView mTvNoNetwork;

    @BindView(R.id.btn_retry)
    AppCompatButton mBtnRetry;

    @BindView(R.id.group_no_internet)
    Group mGroupNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        getWindow().setAllowEnterTransitionOverlap(false);

        supportPostponeEnterTransition();
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM_POSITION);
        }

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        assert mRecyclerView != null;
        mViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        // Handle changes emitted by LiveData
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse != null) {
                if (apiResponse.getError() != null) {
                    mTvNoNetwork.setText(apiResponse.getError().getMessage());
                    mGroupNoInternet.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    setupRecyclerView(mRecyclerView, apiResponse.getMovieResponses().results);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mGroupNoInternet.setVisibility(View.GONE);
                }
            }
        });

        if (NetworkUtils.isOnline(this)) {
            loadMovie();
            mGroupNoInternet.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mGroupNoInternet.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mBtnRetry.setOnClickListener(view -> loadMovie());
        }
    }

    private void loadMovie() {
        if (TextUtils.isEmpty(Config.API_KEY)) {
            showAlertDialogIfNoAPIKey();
        } else {
            if (mViewModel != null) {
                if (mSelectedItem == 1) {
                    mViewModel.loadHighestRatedMovies();
                } else {
                    mViewModel.loadMostPopularMovies();
                }
            }
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<MovieResult> movieResults) {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(new MovieRecyclerViewAdapter(this, movieResults, new MovieClickListener() {
            @Override
            public void onMovieClicked(MovieResult movieResult, int colorPalette, AppCompatImageView moviePoster) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(MovieDetailFragment.ARG_ITEM_ID, movieResult);
                    arguments.putString(ARG_ITEM_TRANSITION_ID, ViewCompat.getTransitionName(moviePoster));
                    arguments.putInt(ARG_ITEM_COLOR_PALETTE, colorPalette);

                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, movieResult);
                    intent.putExtra(ARG_ITEM_TRANSITION_ID, ViewCompat.getTransitionName(moviePoster));
                    intent.putExtra(ARG_ITEM_COLOR_PALETTE, colorPalette);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MovieListActivity.this,
                            moviePoster,
                            ViewCompat.getTransitionName(moviePoster));

                    startActivity(intent, options.toBundle());
                }
            }
        }));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_POSITION, mSelectedItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_most_popular:
                mSelectedItem = 0;
                mViewModel.loadMostPopularMovies();
                return true;
            case R.id.menu_highest_rated:
                mSelectedItem = 1;
                mViewModel.loadHighestRatedMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlertDialogIfNoAPIKey() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getString(R.string.no_api_key_error_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}