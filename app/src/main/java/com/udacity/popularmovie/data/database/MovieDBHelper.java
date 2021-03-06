package com.udacity.popularmovie.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MovieDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_MOVIES + "("
                + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " LONG PRIMARY KEY, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_RATING + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_POPULAR + " BOOLEAN NOT NULL CHECK (" + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_POPULAR + " IN (0,1)), "
                + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_HIGHEST_RATED + " BOOLEAN NOT NULL CHECK (" + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_HIGHEST_RATED + " IN (0,1)), "
                + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE + " BOOLEAN NOT NULL CHECK (" + MovieContract.MovieEntry.COLUMN_MOVIE_SORT_TYPE_IS_FAVORITE + " IN (0,1)), "
                + MovieContract.MovieEntry.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_MOVIES);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                MovieContract.MovieEntry.TABLE_MOVIES + "'");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
