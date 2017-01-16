package com.nvmanh.themoviedb.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.databinding.FragmentMoviesBinding;

/**
 * Created by manhktx on 1/14/17.
 */

public class MovieDetailFragment extends Fragment implements DetailContract.View {
    private static MovieDetailFragment mSelf;
    private FragmentMoviesBinding mBinding;
    private DetailContract.Presenter mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return mBinding.getRoot();
    }

    public static MovieDetailFragment getInstance() {
        if (mSelf == null) mSelf = new MovieDetailFragment();
        return mSelf;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
