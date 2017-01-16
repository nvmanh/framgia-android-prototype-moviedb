package com.nvmanh.themoviedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.nvmanh.themoviedb.base.BaseActivity;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.GenreWrapper;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.data.source.remote.APIService;
import com.nvmanh.themoviedb.data.source.remote.RequestHelper;
import com.nvmanh.themoviedb.main.MainActivity;
import com.nvmanh.themoviedb.utils.SimpleSubscribe;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class SplashActivity extends AwesomeSplash {
    private CompositeSubscription mSubscription;
    private MoviesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        //Customize Logo
        configSplash.setLogoSplash(R.drawable.splash); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);
        //Customize Title
        configSplash.setTitleSplash(getString(R.string.app_name));
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        repository = Injection.provideTasksRepository(this);
        List<Genre> genres = repository.getGenres();
        if (genres != null && !genres.isEmpty()) {
            startMain();
            return;
        }
        BaseSchedulerProvider provider = Injection.provideSchedulerProvider();
        Subscription subscription = RequestHelper.getRequest()
                .getGenres(APIService.API_KEY, null)
                .subscribeOn(provider.computation())
                .observeOn(provider.ui())
                .subscribe(new SimpleSubscribe<GenreWrapper>() {
                    @Override
                    public void onSuccess(GenreWrapper genreWrapper) {
                        showGenres(genreWrapper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(e.getMessage());
                    }
                });
        mSubscription.add(subscription);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showGenres(GenreWrapper genreWrapper) {
        if (genreWrapper == null || genreWrapper.getGenres() == null || genreWrapper.getGenres()
                .isEmpty()) {
            showError("Error occurs!");
            finish();
        } else {
            repository.saveGenres(genreWrapper.getGenres());
            startMain();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSubscription.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startMain() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
