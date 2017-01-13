package com.nvmanh.themoviedb.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
        mMainBinding.pager.setAdapter(new MoviesPager(getSupportFragmentManager()));
        mMainBinding.tabLayout.setupWithViewPager(mMainBinding.pager);
        mMainBinding.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = position == 0 ? MoviesFragment.getInstance()
                        : FavoritesFragment.getInstance();
                selectedTab(fragment);
            }
        });
        //for the first show
        selectedTab(MoviesFragment.getInstance());
    }

    void selectedTab(BaseFragment fragment) {
        fragment.setPresenter(mPresenter);
        fragment.onTabSelected();
    }
}
