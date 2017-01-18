package com.nvmanh.themoviedb.detail;

import com.nvmanh.themoviedb.BasePresenter;
import com.nvmanh.themoviedb.BaseView;
import com.nvmanh.themoviedb.data.Movie;
import java.util.List;

/**
 * Created by manhktx on 1/14/17.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        /**
         * set {@link Movie} into view
         *
         * @param movie {@link Movie}
         */
        void setMovie(Movie movie);

        /**
         * change favorite status of movie
         *
         * @param favorite boolean true/false
         */
        void updateFavorite(boolean favorite);

        /**
         * display similar movies to current movie in view
         *
         * @param movies {@link List<Movie>}
         */
        void showRelated(List<Movie> movies);

        /**
         * show detail of similar movie when user select it
         *
         * @param movie {@link Movie}
         */
        void onRelatedDetail(Movie movie);
    }

    interface Presenter extends BasePresenter {
        /**
         * check if movie is like or not
         */
        void checkFavorite();

        /**
         * start request similar movies with current movie
         */
        void loadRelated();

        /**
         * handle action like or dislike by user
         */
        void onFavoriteChanged();

        /**
         * handle action select a similar movie by user
         */
        void showRelatedDetail(Movie movie);

        /**
         * to notify view we are loading data or not
         *
         * @return true = loading, false = load complete
         */
        boolean isLoading();
    }
}
