package com.nvmanh.themoviedb.main.movies;

import android.view.View;

import com.nvmanh.themoviedb.base.BaseFragment;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesFragment extends BaseFragment {
    private static MoviesFragment mSelf;

    public static MoviesFragment getInstance() {
        if (mSelf == null) {
            mSelf = new MoviesFragment();
        }
        return mSelf;
    }

    @Override
    public boolean isFavoriteScreen() {
        return false;
    }

    @Override
    public void showNoMovie() {
    }
}
