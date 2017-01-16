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
        void setMovie(Movie movie);
        void updateFavorite(boolean favorite);
        void showRelated(List<Movie> movies);
        void onRelatedDetail(Movie movie);
    }

    interface Presenter extends BasePresenter {
        void checkFavorite();
        void loadRelated();
        void onFavoriteChanged();
        void showRelatedDetail(Movie movie);
        boolean isLoading();
    }
}
