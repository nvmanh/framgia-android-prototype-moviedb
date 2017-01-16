package com.nvmanh.themoviedb.main.favorites;

import android.os.Bundle;
import android.view.View;
import com.nvmanh.themoviedb.base.BaseFragment;
import com.nvmanh.themoviedb.data.Movie;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class FavoritesFragment extends BaseFragment {
    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isFavoriteScreen() {
        return true;
    }

    @Override
    protected void onLoad(int page) {
        mPresenter.loadFavorites(page);
    }

    @Override
    public void onTabSelected() {
        super.onTabSelected();
        showLoading();
        if (binding == null || binding.list.getAdapter().getItemCount() > 0) {
            hideLoading();
        }
        clear();
        mPresenter.loadFavorites(1);
    }

    @Override
    public void showNoMovie() {
        hideLoading();
        binding.list.setVisibility(View.GONE);
        binding.noMovie.setVisibility(View.VISIBLE);
    }
}
