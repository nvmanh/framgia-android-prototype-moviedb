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
import java.util.List;
import rx.Observable;

/**
 * Main entry point for accessing tasks data.
 * <p>
 */
public interface MoviesDataSource {

    Observable<List<Movie>> getMovies();

    Observable<Movie> getMovie(int id);

    /**
     * @param page >=1
     * @param limit = 20
     * @return {@link MovieWrapper}
     */
    Observable<MovieWrapper> getMovies(int page, int limit);

    Observable<List<Movie>> getFavorites();

    Observable<MovieWrapper> getFavorites(int page, int limit);

    Movie getFavorite(int id);

    void addFavorite(Movie movie);

    void removeFavorite(int id);

    void removeAllFavorites();

    void saveGenres(List<Genre> genres);

    List<Genre> getGenres();

    String getGenres(int... ids);
}
