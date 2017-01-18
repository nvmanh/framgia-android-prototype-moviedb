package com.nvmanh.themoviedb.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.databinding.FragmentMoviesBinding;
import com.nvmanh.themoviedb.detail.MovieDetailActivity;
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
    protected MoviesContract.Presenter mPresenter;
    private boolean mLoading;
    protected int mCurrentPage;
    private int mTotal = -1;
    protected FragmentMoviesBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.list.setLayoutManager(linearLayoutManager);
        binding.list.setAdapter(new MoviesAdapter());
        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                    mLoading = true;
                }
            }
        });
        return binding.getRoot();
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
        if (isFavoriteScreen()) return;
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
        binding.list.setVisibility(View.VISIBLE);
        binding.noMovie.setVisibility(View.GONE);
        ((MoviesAdapter) binding.list.getAdapter()).setPresenter(mPresenter);
        ((MoviesAdapter) binding.list.getAdapter()).add(movies);
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
        if (mPresenter == null || binding.list.getAdapter().getItemCount() != 0) return;
        mPresenter.subscribe();
    }

    @Override
    public void onTabSelected() {
        if (mPresenter == null) return;
        mPresenter.unSubscribe();
        mPresenter.setView(this);
        if (binding == null || binding.list.getAdapter().getItemCount() != 0) {
            return;
        }
        mPresenter.subscribe();
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
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Movie.class.getName(), movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public int getCurrentPage() {
        return mCurrentPage;
    }

    @Override
    public void clear() {
        if (binding == null) return;
        setCurrentPage(0);
        binding.list.setAdapter(new MoviesAdapter());
    }

    @Override
    public void showNoMovie() {
        if (!isFavoriteScreen()) return;
        hideLoading();
        binding.list.setVisibility(View.GONE);
        binding.noMovie.setVisibility(View.VISIBLE);
    }

    //Notice: should use app context, if not the fragment
    // context may be null when fragment not attached to activity yet
    public abstract String getPageTitle();
}