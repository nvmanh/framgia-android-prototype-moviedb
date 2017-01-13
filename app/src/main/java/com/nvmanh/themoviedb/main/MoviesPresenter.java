package com.nvmanh.themoviedb.main;

import com.android.annotations.NonNull;
import com.google.common.base.Preconditions;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.data.source.remote.APIService;
import com.nvmanh.themoviedb.utils.InternetUtil;
import com.nvmanh.themoviedb.utils.SimpleSubscribe;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class MoviesPresenter implements MoviesContract.Presenter {
    private MoviesRepository mMoviesRepository;
    private MoviesContract.View mMoviesView;
    private BaseSchedulerProvider mBaseSchedulerProvider;
    @android.support.annotation.NonNull
    private CompositeSubscription mSubscriptions;

    public MoviesPresenter(@NonNull MoviesRepository moviesRepository,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mMoviesRepository = moviesRepository;
        this.mBaseSchedulerProvider = schedulerProvider;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadMovies(int page) {
        if (!InternetUtil.isConnectingToInternet()) {
            mMoviesView.showNetworkError();
            return;
        }
        mMoviesView.showLoading();
        Subscription subscription = mMoviesRepository.getMovies(page, APIService.DEFAULT_LIMIT)
                .subscribeOn(mBaseSchedulerProvider.computation())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new SimpleSubscribe<MovieWrapper>() {
                    @Override
                    public void onSuccess(MovieWrapper movieWrapper) {
                        if (movieWrapper == null || movieWrapper.getResults() == null) return;
                        mMoviesView.setTotal(movieWrapper.getTotalResults());
                        mMoviesView.setCurrentPage(movieWrapper.getPage());
                        List<Movie> movies = movieWrapper.getResults();
                        List<Genre> genres = mMoviesRepository.getGenres();
                        Map<Integer, Genre> map = new HashMap<>();
                        for (Genre genre : genres) {
                            map.put(genre.getId(), genre);
                        }
                        for (Movie movie : movies) {
                            StringBuilder builder = new StringBuilder();
                            for (int id : movie.getGenreIds()) {
                                if (!map.containsKey(id)) continue;
                                builder.append(map.get(id).getName()).append(",");
                            }
                            if (builder.length() > 0) {
                                movie.setGenreList(
                                        builder.toString().substring(0, builder.length() - 1));
                            }
                        }
                        mMoviesView.showMovies(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMoviesView.showError(e);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadFavorites(int page) {
        //mMoviesView.showLoading();
        Subscription subscription = mMoviesRepository.getFavorites(page, APIService.DEFAULT_LIMIT)
                .subscribeOn(mBaseSchedulerProvider.computation())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new SimpleSubscribe<MovieWrapper>() {
                    @Override
                    public void onSuccess(MovieWrapper movieWrapper) {
                        mMoviesView.setTotal(movieWrapper.getTotalResults());
                        mMoviesView.setCurrentPage(movieWrapper.getPage());
                        mMoviesView.showMovies(movieWrapper.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMoviesView.showError(e);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void delete(int movieId) {
        mMoviesRepository.removeFavorite(movieId);
    }

    @Override
    public void deleteAll() {
        mMoviesRepository.removeAllFavorites();
    }

    @Override
    public void onShowDetail(Movie movie) {
        if (mMoviesView == null) return;
        mMoviesView.showDetail(movie);
    }

    @Override
    public void setView(MoviesContract.View view) {
        mMoviesView = Preconditions.checkNotNull(view);
        mMoviesView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mMoviesView.isFavoriteScreen()) {
            loadFavorites(1);
        } else {
            loadMovies(1);
        }
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}