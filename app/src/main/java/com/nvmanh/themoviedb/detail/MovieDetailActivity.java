package com.nvmanh.themoviedb.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.nvmanh.themoviedb.Injection;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.base.BaseActivity;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.databinding.ActivityDetailBinding;
import com.nvmanh.themoviedb.utils.ActivityUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by manhktx on 1/14/17.
 */

public class MovieDetailActivity extends BaseActivity {
    private CompositeSubscription mSubscription;
    private ActivityDetailBinding mBinding;
    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mSubscription = new CompositeSubscription();
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Movie movie = (Movie) getIntent().getExtras().getSerializable(Movie.class.getName());
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.newInstance();
            actionBar.setTitle(movie.getTitle());
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), movieDetailFragment,
                    R.id.container);

        }
        mPresenter = new DetailPresenter(movie, movieDetailFragment, Injection.provideTasksRepository(this), Injection.provideSchedulerProvider());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSubscription.clear();
    }
}
