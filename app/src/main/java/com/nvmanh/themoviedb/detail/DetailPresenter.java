package com.nvmanh.themoviedb.detail;

import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.MovieWrapper;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.utils.Common;
import com.nvmanh.themoviedb.utils.SimpleSubscribe;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;

import rx.Subscription;
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
    private int currentPage = 0;
    private boolean isLoading = false;
    private int mTotalPage = -1;

    public DetailPresenter(Movie movie, DetailContract.View view, MoviesRepository repository,
            BaseSchedulerProvider provider) {
        this.mMovie = movie;
        this.mRepository = repository;
        this.mProvider = provider;
        this.mView = view;
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mView == null) return;
        checkFavorite();
        mView.setPresenter(this);
        loadRelated();
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void checkFavorite() {
        Movie movie = mRepository.getFavorite(mMovie.getId());
        if (movie != null) mMovie = movie;
        mView.setMovie(mMovie);
    }

    @Override
    public void loadRelated() {
        if (currentPage == mTotalPage || !Common.isConnectingToInternet()) {
            isLoading = false;
            return;
        }
        isLoading = true;
        Subscription subscription = mRepository.getSimilarMovies(mMovie.getId(), currentPage + 1)
                .subscribeOn(mProvider.computation())
                .observeOn(mProvider.ui())
                .subscribe(new SimpleSubscribe<MovieWrapper>() {
                    @Override
                    public void onSuccess(MovieWrapper movieWrapper) {
                        isLoading = false;
                        if (movieWrapper == null
                                || movieWrapper.getResults() == null
                                || movieWrapper.getResults().isEmpty()) {
                            mView.showRelated(null);
                        } else {
                            currentPage++;
                            mTotalPage = movieWrapper.getTotalPages();
                            mView.showRelated(movieWrapper.getResults());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading = false;
                        mView.showRelated(null);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void onFavoriteChanged() {
        boolean favorite = !mMovie.isFavorite();
        mMovie.setFavorite(favorite);
        if (favorite) {
            mRepository.addFavorite(mMovie);
        } else {
            mRepository.removeFavorite(mMovie.getId());
        }
        mView.updateFavorite(favorite);
    }

    @Override
    public void showRelatedDetail(Movie movie) {
        Common.updateGenres(movie, mRepository);
        mView.onRelatedDetail(movie);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }
}
