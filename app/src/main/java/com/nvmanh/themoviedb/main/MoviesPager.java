package com.nvmanh.themoviedb.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.base.BaseFragment;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesPager extends FragmentPagerAdapter {
    SparseArray<BaseFragment> mFragments;

    public MoviesPager(FragmentManager fm, SparseArray<BaseFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return App.self()
                .getString(position == 0 ? R.string.playing_movies : R.string.favorite_movies);
    }
}
