/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nvmanh.themoviedb.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.utils.Common;
import java.util.List;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p/>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class MoviesRepository implements MoviesDataSource {

    @Nullable
    private static MoviesRepository INSTANCE = null;

    @NonNull
    private final MoviesDataSource mMoviesRemoteDataSource;

    @NonNull
    private final MoviesDataSource mMoviesLocalDataSource;

    // Prevent direct instantiation.
    private MoviesRepository(@NonNull MoviesDataSource tasksRemoteDataSource,
            @NonNull MoviesDataSource tasksLocalDataSource) {
        mMoviesRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mMoviesLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource the device storage data source
     * @return the {@link MoviesRepository} instance
     */
    public static MoviesRepository getInstance(@NonNull MoviesDataSource tasksRemoteDataSource,
            @NonNull MoviesDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(MoviesDataSource, MoviesDataSource)} to create a new
     * instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Movie>> getMovies() {
        return mMoviesRemoteDataSource.getMovies();
    }

    @Override
    public Observable<Movie> getMovie(int id) {
        return mMoviesRemoteDataSource.getMovie(id);
    }

    @Override
    public Observable<MovieWrapper> getMovies(int page, int limit) {
        return mMoviesRemoteDataSource.getMovies(page, limit);
    }

    @Override
    public Observable<List<Movie>> getFavorites() {
        return mMoviesLocalDataSource.getFavorites();
    }

    @Override
    public Observable<MovieWrapper> getFavorites(int page, int limit) {
        return mMoviesLocalDataSource.getFavorites(page, limit);
    }

    @Override
    public Movie getFavorite(int id) {
        return mMoviesLocalDataSource.getFavorite(id);
    }

    @Override
    public void addFavorite(Movie movie) {
        mMoviesLocalDataSource.addFavorite(movie);
        mMoviesRemoteDataSource.addFavorite(movie);
    }

    @Override
    public void removeFavorite(int id) {
        mMoviesRemoteDataSource.removeFavorite(id);
        mMoviesLocalDataSource.removeFavorite(id);
    }

    @Override
    public void removeAllFavorites() {
        mMoviesLocalDataSource.removeAllFavorites();
        mMoviesRemoteDataSource.removeAllFavorites();
    }

    @Override
    public void saveGenres(List<Genre> genres) {
        mMoviesLocalDataSource.saveGenres(genres);
    }

    @Override
    public List<Genre> getGenres() {
        return mMoviesLocalDataSource.getGenres();
    }

    @Override
    public String getGenres(int... ids) {
        return mMoviesLocalDataSource.getGenres(ids);
    }

    @Override
    public Observable<MovieWrapper> getSimilarMovies(int movieId, int page) {
        return mMoviesRemoteDataSource.getSimilarMovies(movieId, page);
    }
}
