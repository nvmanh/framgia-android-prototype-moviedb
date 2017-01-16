package com.nvmanh.themoviedb.main.movies;

import android.os.Bundle;
import com.nvmanh.themoviedb.base.BaseFragment;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesFragment extends BaseFragment {
    public static MoviesFragment newInstance() {
         Bundle args = new Bundle();
         MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isFavoriteScreen() {
        return false;
    }

    @Override
    public void showNoMovie() {
    }

    @Override
    protected void onLoad(int page) {
        mPresenter.loadMovies(page);
    }
}
