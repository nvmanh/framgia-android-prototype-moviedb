package com.nvmanh.themoviedb;

import android.app.Application;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class App extends Application {
    private volatile static App _self;

    public static App self() {
        return _self;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _self = this;
    }
}
