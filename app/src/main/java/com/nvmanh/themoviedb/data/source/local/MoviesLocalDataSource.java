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

package com.nvmanh.themoviedb.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.MoviesDataSource;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;
import java.sql.SQLException;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class MoviesLocalDataSource implements MoviesDataSource {

    @Nullable
    private static MoviesLocalDataSource INSTANCE;

    @NonNull
    private final DataBaseHelper mDatabaseHelper;

    // Prevent direct instantiation.
    private MoviesLocalDataSource(@NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        mDatabaseHelper = OpenHelperManager.getHelper(App.self(), DataBaseHelper.class);
    }

    public static MoviesLocalDataSource getInstance(@NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Movie>> getMovies() {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                try {
                    subscriber.onNext(mDatabaseHelper.getCachedDao(Movie.class).queryForAll());
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<Movie> getMovie(final int id) {
        return Observable.create(new Observable.OnSubscribe<Movie>() {
            @Override
            public void call(Subscriber<? super Movie> subscriber) {
                try {
                    subscriber.onNext(mDatabaseHelper.getCachedDao(Movie.class).queryForId(id));
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<MovieWrapper> getMovies(final int page, final int limit) {
        return Observable.create(new Observable.OnSubscribe<MovieWrapper>() {
            @Override
            public void call(Subscriber<? super MovieWrapper> subscriber) {
                Dao<Movie, Integer> dao = mDatabaseHelper.getCachedDao(Movie.class);
                try {
                    int total = (int) dao.countOf();
                    int totalPage = total % limit == 0 ? total / limit : (total / limit) + 1;
                    MovieWrapper wrapper = new MovieWrapper();
                    wrapper.setPage(page);
                    wrapper.setTotalPages(totalPage);
                    wrapper.setTotalResults(total);
                    QueryBuilder<Movie, Integer> queryBuilder = dao.queryBuilder();
                    queryBuilder.offset((page - 1) * limit).limit(limit);
                    List<Movie> movies = dao.query(queryBuilder.prepare());
                    wrapper.setResults(movies);
                    subscriber.onNext(wrapper);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<List<Movie>> getFavorites() {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                try {
                    subscriber.onNext(mDatabaseHelper.getCachedDao(Movie.class)
                            .queryForEq(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_FAVORITE,
                                    1));
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @Override
    public Observable<MovieWrapper> getFavorites(int page, int limit) {
        return getMovies(page, limit);
    }

    @Override
    public Movie getFavorite(int id) {
        try {
            return mDatabaseHelper.getCachedDao(Movie.class).queryForId(id);
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return null;
    }

    @Override
    public void addFavorite(Movie movie) {
        try {
            mDatabaseHelper.getCachedDao(Movie.class).createOrUpdate(movie);
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void removeFavorite(int id) {
        try {
            mDatabaseHelper.getCachedDao(Movie.class).deleteById(id);
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void removeAllFavorites() {
        Dao<Movie, Integer> dao = mDatabaseHelper.getCachedDao(Movie.class);
        try {
            DeleteBuilder<Movie, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder.where().eq(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_FAVORITE, 1);
            dao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void saveGenres(List<Genre> genres) {
        Dao<Genre, Integer> dao = mDatabaseHelper.getCachedDao(Genre.class);
        try {
            for (Genre genre : genres) {
                dao.createOrUpdate(genre);
            }
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public List<Genre> getGenres() {
        Dao<Genre, Integer> dao = mDatabaseHelper.getCachedDao(Genre.class);
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return null;
    }

    @Override
    public String getGenres(int... ids) {
        Dao<Genre, Integer> dao = mDatabaseHelper.getCachedDao(Genre.class);
        StringBuilder builder = new StringBuilder();
        try {
            List<Genre> genres = dao.queryForAll();
            if (genres == null || genres.isEmpty()) return builder.toString();
            for (Genre genre : genres) {
                for (int id : ids) {
                    if (id != genre.getId()) continue;
                    builder.append(genre.getName()).append(",");
                }
            }
            return builder.toString().substring(0, builder.length() - 1);
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
        return builder.toString();
    }

    @Override
    public Observable<MovieWrapper> getSimilarMovies(int movieId, int page) {
        return null;
    }
}
