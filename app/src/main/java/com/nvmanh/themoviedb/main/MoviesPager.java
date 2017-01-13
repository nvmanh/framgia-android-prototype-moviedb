package com.nvmanh.themoviedb.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.main.favorites.FavoritesFragment;
import com.nvmanh.themoviedb.main.movies.MoviesFragment;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesPager extends FragmentPagerAdapter {
    public MoviesPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? MoviesFragment.getInstance() : FavoritesFragment.getInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return App.self()
                .getString(position == 0 ? R.string.playing_movies : R.string.favorite_movies);
    }
}
