package com.nvmanh.themoviedb.main;

import android.util.Log;
import com.android.annotations.NonNull;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.data.source.remote.APIService;
import com.nvmanh.themoviedb.utils.Common;
import com.nvmanh.themoviedb.utils.SimpleSubscribe;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;
import java.util.List;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class MoviesPresenter implements MoviesContract.Presenter {
    private MoviesRepository mMoviesRepository;
    private MoviesContract.View mMoviesView;
    @android.support.annotation.NonNull
    private CompositeSubscription mSubscriptions;

    public MoviesPresenter(@NonNull MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loadMovies(final int page) {
        if (!Common.isConnectingToInternet() && !mMoviesView.isFavoriteScreen()) {
            mMoviesView.showNetworkError();
            return;
        }
        mMoviesView.showLoading();
        SimpleSubscribe<MovieWrapper> simpleSubscribe = new SimpleSubscribe<MovieWrapper>() {
            @Override
            public void onSuccess(MovieWrapper movieWrapper) {
                if (movieWrapper == null
                        || movieWrapper.getPage() == mMoviesView.getCurrentPage()
                        || movieWrapper.getResults() == null) {
                    if (page == 1) mMoviesView.showNoMovie();
                    return;
                }
                if (page == 1) mMoviesView.clear();
                mMoviesView.setTotal(movieWrapper.getTotalResults());
                mMoviesView.setCurrentPage(movieWrapper.getPage());
                List<Movie> movies = movieWrapper.getResults();
                Common.updateGenres(movies, mMoviesRepository);
                mMoviesView.showMovies(movies);
            }

            @Override
            public void onError(Throwable e) {
                mMoviesView.showError(e);
            }
        };
        Subscription subscription;
        if (mMoviesView.isFavoriteScreen()) {
            subscription = mMoviesRepository.getFavorites(page, APIService.DEFAULT_LIMIT)
                    .subscribe(simpleSubscribe);
        } else {
            subscription = mMoviesRepository.getMovies(page, APIService.DEFAULT_LIMIT)
                    .subscribe(simpleSubscribe);
        }
        mSubscriptions.add(subscription);
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
        loadMovies(1);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}