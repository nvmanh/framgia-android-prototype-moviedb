package com.nvmanh.themoviedb.detail;

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
import com.bumptech.glide.Glide;
import com.google.common.base.Preconditions;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.databinding.FragmentDetailBinding;
import com.nvmanh.themoviedb.main.MoviesAdapter;
import com.nvmanh.themoviedb.utils.Common;
import java.util.List;

/**
 * Created by manhktx on 1/14/17.
 */

public class MovieDetailFragment extends Fragment implements DetailContract.View {
    private FragmentDetailBinding mBinding;
    private DetailContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.similar.setLayoutManager(linearLayoutManager);
        mBinding.similar.setAdapter(new SimilarMoviesAdapter());
        mBinding.similar.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mPresenter == null) {
                    return;
                }
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!mPresenter.isLoading() && lastVisibleItem == totalItem - 1) {
                    mPresenter.loadRelated();
                }
            }
        });
        return mBinding.getRoot();
    }

    public static MovieDetailFragment newInstance() {
        Bundle args = new Bundle();
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null) return;
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter == null) return;
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
        if (mBinding == null) return;
        mBinding.setPresenter(mPresenter);
        ((SimilarMoviesAdapter) mBinding.similar.getAdapter()).setPresenter(mPresenter);
        mBinding.similar.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setMovie(Movie movie) {
        mBinding.setMovie(movie);
        Glide.with(getActivity())
                .load(Common.getImageMovie(movie.getPosterPath()))
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .into(mBinding.poster);
        Glide.with(getActivity())
                .load(Common.getImageMovie(movie.getBackdropPath()))
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .into(mBinding.backdrop);
    }

    @Override
    public void updateFavorite(boolean favorite) {
        mBinding.like.setBackgroundResource(favorite ? R.drawable.like : R.drawable.dislike);
    }

    @Override
    public void showRelated(List<Movie> movies) {
        if (mBinding == null) return;
        if (movies == null) {
            mBinding.similar.setVisibility(View.GONE);
            return;
        }
        mBinding.loading.setVisibility(View.GONE);
        mBinding.similar.setVisibility(View.VISIBLE);
        mBinding.similar.invalidate();
        SimilarMoviesAdapter adapter = (SimilarMoviesAdapter) mBinding.similar.getAdapter();
        adapter.add(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRelatedDetail(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Movie.class.getName(), movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
