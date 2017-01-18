package com.nvmanh.themoviedb.main;

import com.nvmanh.themoviedb.BasePresenter;
import com.nvmanh.themoviedb.BaseView;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.main.favorites.FavoritesFragment;
import com.nvmanh.themoviedb.main.movies.MoviesFragment;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public interface MoviesContract {
    interface View extends BaseView<Presenter> {
        /**
         * inform no network connection
         */
        void showNetworkError();

        /**
         * display movie list on view
         *
         * @param movies {@link List<Movie>}
         */
        void showMovies(List<Movie> movies);

        /**
         * show dialog loading to inform user that we are downloading data
         */
        void showLoading();

        /**
         * hide loading dialog
         */
        void hideLoading();

        /**
         * show error when parse data, and all request problems
         */
        void showError(Throwable e);

        /**
         * check current view is list from api service or favorite screen
         *
         * @return true {@link MoviesFragment} false {@link FavoritesFragment}
         */
        boolean isFavoriteScreen();

        /**
         * handle event on tab change to view
         */
        void onTabSelected();

        /**
         * go to detail screen of movie
         *
         * @param movie {@link Movie}
         */
        void showDetail(Movie movie);

        /**
         * set current page for paging function
         *
         * @param page = int from 1,...n
         */
        void setCurrentPage(int page);

        /**
         * return current page for {@link Presenter}
         *
         * @return 1, 2... n
         */
        int getCurrentPage();

        /**
         * all records we received from database for favorite or all result movies from api service
         *
         * @param total : int number
         */
        void setTotal(int total);

        /**
         * using in {@link FavoritesFragment}
         */
        void showNoMovie();

        /**
         * using in {@link FavoritesFragment} when user clear all favorite movies
         */
        void clear();
    }

    interface Presenter extends BasePresenter {
        /**
         * request movies from api service or local database
         *
         * @param page from 1, 2... n
         */
        void loadMovies(int page);

        /**
         * handle action click a movies by user
         */
        void onShowDetail(Movie movie);

        /**
         * update data to view when loading done
         *
         * @param view {@link View}
         */
        void setView(View view);
    }
}
