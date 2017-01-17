package com.nvmanh.themoviedb.main;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import com.nvmanh.themoviedb.Injection;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.base.BaseActivity;
import com.nvmanh.themoviedb.base.BaseFragment;
import com.nvmanh.themoviedb.databinding.ActivityMainBinding;
import com.nvmanh.themoviedb.main.favorites.FavoritesFragment;
import com.nvmanh.themoviedb.main.movies.MoviesFragment;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mMainBinding;
    private MoviesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        mPresenter = new MoviesPresenter(Injection.provideTasksRepository(this),
                Injection.provideSchedulerProvider());
        setSupportActionBar(mMainBinding.toolbar);
        mMainBinding.tabLayout.newTab().setText(R.string.playing_movies);
        mMainBinding.tabLayout.newTab().setText(R.string.favorite_movies);
        mMainBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        SparseArray<BaseFragment> mFragments = new SparseArray<>();
        mFragments.put(0, MoviesFragment.newInstance());
        mFragments.put(1, FavoritesFragment.newInstance());
        final MoviesPager pager = new MoviesPager(getSupportFragmentManager(), mFragments);
        mMainBinding.pager.setAdapter(pager);
        mMainBinding.tabLayout.setupWithViewPager(mMainBinding.pager);
        mMainBinding.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                selectedTab(pager.getItem(position));
            }
        });
        //for the first show
        selectedTab(pager.getItem(0));
    }

    void selectedTab(BaseFragment fragment) {
        fragment.setPresenter(mPresenter);
        fragment.onTabSelected();
    }

    @Override
    public void onBackPressed() {
        if (mMainBinding.pager.getCurrentItem() != 0) {
            mMainBinding.pager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }
}
