package com.nvmanh.themoviedb.main.favorites;

import android.os.Bundle;
import android.view.View;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
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
    public void onTabSelected() {
        clear();
        super.onTabSelected();
    }

    @Override
    public String getPageTitle() {
        return App.self().getString(R.string.favorite_movies);
    }
}
