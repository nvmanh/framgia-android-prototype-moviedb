package com.nvmanh.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nvmanh.themoviedb.base.BaseActivity;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.GenreWrapper;
import com.nvmanh.themoviedb.data.source.MoviesRepository;
import com.nvmanh.themoviedb.data.source.remote.APIService;
import com.nvmanh.themoviedb.data.source.remote.RequestHelper;
import com.nvmanh.themoviedb.main.MainActivity;
import com.nvmanh.themoviedb.utils.SimpleSubscribe;
import com.nvmanh.themoviedb.utils.schedulers.BaseSchedulerProvider;
import java.util.List;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class SplashActivity extends BaseActivity {
    private CompositeSubscription mSubscription;
    private MoviesRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(params);
        layout.setGravity(Gravity.CENTER);
        ProgressBar mProgressBar = new ProgressBar(this);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        mProgressBar.setIndeterminate(true);
        layout.addView(mProgressBar);
        setContentView(layout);
        repository = Injection.provideTasksRepository(this);
        List<Genre> genres = repository.getGenres();
        if (genres != null && !genres.isEmpty()) {
            startMain();
            return;
        }
        mSubscription = new CompositeSubscription();
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
