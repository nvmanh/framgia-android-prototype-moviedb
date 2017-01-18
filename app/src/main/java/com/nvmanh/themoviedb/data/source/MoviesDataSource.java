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

import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.local.MoviesLocalDataSource;
import com.nvmanh.themoviedb.data.source.remote.MoviesRemoteDataSource;
import java.util.List;
import rx.Observable;

/**
 * Main entry point for accessing tasks data.
 * <p>
 */
public interface MoviesDataSource {

    /**
     * using to request movies from the moviedb in {@link MoviesRemoteDataSource}
     */
    Observable<List<Movie>> getMovies();

    /**
     * using to request detail information of a movie in {@link MoviesRemoteDataSource}
     *
     * @param id of a movie
     * @return {@link Movie}
     */
    Observable<Movie> getMovie(int id);

    /**
     * @param page >=1
     * @param limit = 20
     * @return {@link MovieWrapper}
     */
    Observable<MovieWrapper> getMovies(int page, int limit);

    /**
     * request all favorite movies in your local database
     *
     * @return {@link List<Movie>} with Rx call
     */
    Observable<List<Movie>> getFavorites();

    /**
     * request all favorite movies in your local database and limit by page
     *
     * @param page from page
     * @param limit default = 20
     * @return {@link MovieWrapper} with Rx call
     */
    Observable<MovieWrapper> getFavorites(int page, int limit);

    /**
     * get a movie from local database {@link MoviesLocalDataSource} or temporary memory {@link
     * MoviesRemoteDataSource}
     *
     * @param id of a movie
     * @return {@link Movie}
     */
    Movie getFavorite(int id);

    /**
     * save a movie into your local database
     *
     * @param movie {@link Movie}
     */
    void addFavorite(Movie movie);

    /**
     * delete a movie in your local database
     *
     * @param id of movie
     */
    void removeFavorite(int id);

    /**
     * delete all movies in local database
     */
    void removeAllFavorites();

    /**
     * save genres to movies, using to display genre with name for it's id in movie list
     *
     * @param genres @{@link Genre}
     */
    void saveGenres(List<Genre> genres);

    /**
     * get all genres from database
     *
     * @return {@link List<Genre>}
     */
    List<Genre> getGenres();

    /**
     * genre array of genre name from their id
     *
     * @param ids array ids need
     * @return [genre_nam_1. genre_name_2, ...]
     */
    String getGenres(int... ids);

    /**
     * request similar movies with a movie from the moviedb in {@link MoviesRemoteDataSource}
     *
     * @param movieId id of a movie
     * @param page limitation by page
     * @return {@link MovieWrapper}
     */
    Observable<MovieWrapper> getSimilarMovies(int movieId, int page);
}
