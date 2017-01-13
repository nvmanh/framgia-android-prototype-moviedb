package com.nvmanh.themoviedb.main.favorites;

import com.nvmanh.themoviedb.base.BaseFragment;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class FavoritesFragment extends BaseFragment {
    private static FavoritesFragment mSelf;

    public static FavoritesFragment getInstance() {
        if (mSelf == null) {
            mSelf = new FavoritesFragment();
        }
        return mSelf;
    }

    @Override
    public boolean isFavoriteScreen() {
        return true;
    }

    @Override
    public void onTabSelected() {
        super.onTabSelected();
    }
}
