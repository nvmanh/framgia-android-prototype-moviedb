package com.nvmanh.themoviedb.main;

import android.content.Context;
import com.nvmanh.themoviedb.BaseViewModel;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesViewModel extends BaseViewModel {
    private Context mContext;
    private MoviesPresenter mPresenter;

    public MoviesViewModel(Context context, MoviesPresenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }
}
