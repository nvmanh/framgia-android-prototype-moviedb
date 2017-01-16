package com.nvmanh.themoviedb.detail;

import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by manhktx on 1/14/17.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private Movie mMovie;
    private MoviesRepository mRepository;
    private BaseSchedulerProvider mProvider;
    private DetailContract.View mView;
    private CompositeSubscription mSubscription;

    public DetailPresenter(Movie movie, DetailContract.View view, MoviesRepository repository, BaseSchedulerProvider provider) {
        this.mMovie = movie;
        this.mRepository = repository;
        this.mProvider = provider;
        this.mView = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        checkFavorite();
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void addToFavorite() {
        if(mMovie.isFavorite()) return;
        mMovie.setFavorite(true);
        mRepository.addFavorite(mMovie);
        mView.updateFavorite(true);
    }

    @Override
    public void deleteFavorite() {
        mRepository.removeFavorite(mMovie.getId());
        mView.updateFavorite(false);
    }

    @Override
    public void checkFavorite() {
        Movie movie = mRepository.getFavorite(mMovie.getId());
        if (movie != null) mMovie = movie;
        if (mView == null) return;
        mView.setMovie(mMovie);
    }
}
