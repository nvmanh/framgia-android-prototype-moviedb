package com.nvmanh.themoviedb.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.main.LoadingDialog;
import com.nvmanh.themoviedb.main.MoviesAdapter;
import com.nvmanh.themoviedb.main.MoviesContract;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public abstract class BaseFragment extends Fragment implements MoviesContract.View {
    private LoadingDialog mLoadingDialog;
    private AlertDialog mAlertDialog;
    private MoviesContract.Presenter mPresenter;
    private boolean mLoading;
    private int mCurrentPage;
    private int mTotal = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MoviesAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mPresenter == null || recyclerView.getAdapter().getItemCount() == mTotal) {
                    return;
                }
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    mLoading = true;
                    // Scrolled to bottom. Do something here.
                    mPresenter.loadMovies(mCurrentPage + 1);
                    mLoading = false;
                }
            }
        });
        return recyclerView;
    }

    @Override
    public void setTotal(int total) {
        mTotal = total;
    }

    @Override
    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    @Override
    public void showNetworkError() {
        mLoading = false;
        hideLoading();
        buildAlertIfNotExist(getString(R.string.network_error));
        mAlertDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideLoading();
        if (mPresenter == null) return;
        mPresenter.unSubscribe();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mLoading = false;
        hideLoading();
        if (getView() == null) return;
        RecyclerView recyclerView = (RecyclerView) getView();
        ((MoviesAdapter) recyclerView.getAdapter()).setPresenter(mPresenter);
        ((MoviesAdapter) recyclerView.getAdapter()).add(movies);
    }

    @Override
    public void showLoading() {
        if (getActivity() == null || getActivity().isFinishing() || (mLoadingDialog != null
                && mLoadingDialog.isShowing())) {
            return;
        }
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (getActivity() == null
                || getActivity().isFinishing()
                || mLoadingDialog == null
                || !mLoadingDialog.isShowing()) {
            return;
        }
        mLoadingDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView recyclerView = (RecyclerView) getView();
        if (mPresenter == null || recyclerView.getAdapter().getItemCount() != 0) return;
        mPresenter.subscribe();
    }

    @Override
    public void onTabSelected() {
        if (mPresenter == null) return;
        mPresenter.setView(this);
    }

    @Override
    public void showError(Throwable e) {
        mLoading = false;
        hideLoading();
        if (getActivity() == null || getActivity().isFinishing()) return;
        buildAlertIfNotExist(e.getMessage());
        mAlertDialog.show();
    }

    private void buildAlertIfNotExist(String message) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(getActivity()).setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }
        mAlertDialog.setCanceledOnTouchOutside(true);
        mAlertDialog.setTitle(App.self().getString(R.string.app_name));
        mAlertDialog.setMessage(message);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDetail(Movie movie) {
        Log.i("BaseFragment", "showDetail (169): ----------> " + 1);
    }
}
