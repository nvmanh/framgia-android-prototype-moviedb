package com.nvmanh.themoviedb.main;

import com.nvmanh.themoviedb.BasePresenter;
import com.nvmanh.themoviedb.BaseView;
import com.nvmanh.themoviedb.data.Movie;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        void showNetworkError();

        //void reachLimit();

        void showMovies(List<Movie> movies);

        void showLoading();

        void hideLoading();

        void showError(Throwable e);

        boolean isFavoriteScreen();

        void onTabSelected();

        void showDetail(Movie movie);

        void setCurrentPage(int page);

        int getCurrentPage();

        void setTotal(int total);

        void showNoMovie();
    }

    interface Presenter extends BasePresenter {
        void loadMovies(int page);

        void loadFavorites(int page);

        void delete(int movieId);

        void deleteAll();

        void onShowDetail(Movie movie);

        void setView(View view);
    }
}
