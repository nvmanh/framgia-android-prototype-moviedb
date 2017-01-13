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

package com.nvmanh.themoviedb.data.source.remote;

import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.MoviesDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.Subscriber;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
public class MoviesRemoteDataSource implements MoviesDataSource {

    private static MoviesRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<Integer, Movie> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(0);
    }

    public static MoviesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MoviesRemoteDataSource() {
    }

    private List<Movie> toListMovie() {
        List<Movie> movies = new ArrayList<>();
        for (Map.Entry<Integer, Movie> entry : TASKS_SERVICE_DATA.entrySet()) {
            movies.add(entry.getValue());
        }
        return movies;
    }

    //private List<Movie> toListMovieFavorite() {
    //    List<Movie> movies = new ArrayList<>();
    //    for (Map.Entry<Integer, Movie> entry : TASKS_SERVICE_DATA.entrySet()) {
    //        if (!entry.getValue().isFavorite()) continue;
    //        movies.add(entry.getValue());
    //    }
    //    return movies;
    //}

    @Override
    public Observable<List<Movie>> getMovies() {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                subscriber.onNext(toListMovie());
            }
        });
    }

    @Override
    public Observable<Movie> getMovie(final int id) {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {
                subscriber.onNext(TASKS_SERVICE_DATA.get(id));
            }
        });
    }

    @Override
    public Observable<MovieWrapper> getMovies(int page, int limit) {
        return RequestHelper.getRequest()
                .getPlayingMovies(APIService.API_KEY, null, page)
                .compose(ObservableUtils.<MovieWrapper>applyAsyncSchedulers());
    }

    @Override
    public Observable<List<Movie>> getFavorites() {
        //return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
        //    @Override
        //    public void call(Subscriber<? super List<Movie>> subscriber) {
        //        subscriber.onNext(toListMovieFavorite());
        //    }
        //});
        return null;
    }

    @Override
    public Observable<MovieWrapper> getFavorites(int page, int limit) {
        return null;
    }

    @Override
    public void addFavorite(Movie movie) {
        TASKS_SERVICE_DATA.put(movie.getId(), movie);
    }

    @Override
    public void removeFavorite(int id) {
        TASKS_SERVICE_DATA.remove(id);
    }

    @Override
    public void removeAllFavorites() {
        TASKS_SERVICE_DATA.clear();
    }

    @Override
    public void saveGenres(List<Genre> genres) {

    }

    @Override
    public List<Genre> getGenres() {
        return null;
    }

    @Override
    public String getGenres(int... ids) {
        return null;
    }
}
