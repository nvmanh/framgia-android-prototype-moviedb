package com.nvmanh.themoviedb.detail;

import com.nvmanh.themoviedb.BasePresenter;
import com.nvmanh.themoviedb.BaseView;
import com.nvmanh.themoviedb.data.Movie;

/**
 * Created by manhktx on 1/14/17.
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {
        void setMovie(Movie movie);
        void updateFavorite(boolean favorite);
        void showRelated(Movie movie);
    }

    interface Presenter extends BasePresenter {
        void addToFavorite();
        void deleteFavorite();
        void checkFavorite();
        void loadRelated();
    }
}
