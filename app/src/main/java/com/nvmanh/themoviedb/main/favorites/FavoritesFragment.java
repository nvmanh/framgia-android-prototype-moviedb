package com.nvmanh.themoviedb.main.favorites;

import android.view.View;

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
        hideLoading();
        if (binding == null || binding.list.getAdapter().getItemCount() > 0) return;
        binding.list.setVisibility(View.GONE);
        binding.noMovie.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoMovie() {
        hideLoading();
        binding.list.setVisibility(View.GONE);
        binding.noMovie.setVisibility(View.VISIBLE);
    }
}
