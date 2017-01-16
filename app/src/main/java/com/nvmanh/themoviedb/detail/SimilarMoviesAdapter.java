package com.nvmanh.themoviedb.detail;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.nvmanh.themoviedb.App;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.databinding.ItemSimilarBinding;
import com.nvmanh.themoviedb.utils.Common;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 16/01/2017.
 */

public class SimilarMoviesAdapter
        extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesViewHolder> {
    private LayoutInflater mLayoutInflater;
    private DetailContract.Presenter mPresenter;
    private int pxWidth;
    private int pxHeight;
    private List<Movie> mMovies = new ArrayList<>();

    public SimilarMoviesAdapter() {
        Drawable placeHolder = App.self().getResources().getDrawable(R.drawable.bg_default);
        pxWidth = placeHolder.getIntrinsicWidth();
        pxHeight = placeHolder.getIntrinsicHeight();
    }

    public void add(List<Movie> list) {
        this.mMovies.addAll(list);
    }

    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemSimilarBinding binding =
                DataBindingUtil.inflate(mLayoutInflater, R.layout.item_similar, parent, false);
        return new SimilarMoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SimilarMoviesViewHolder holder, int position) {
        holder.bind(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    class SimilarMoviesViewHolder extends RecyclerView.ViewHolder {
        private ItemSimilarBinding mItemSimilarBinding;

        public SimilarMoviesViewHolder(ItemSimilarBinding binding) {
            super(binding.getRoot());
            mItemSimilarBinding = binding;
        }

        void bind(Movie movie) {
            mItemSimilarBinding.setMovie(movie);
            mItemSimilarBinding.setPresenter(mPresenter);
            Glide.with(itemView.getContext())
                    .load(Common.getImageMovie(movie.getPosterPath()))
                    .fitCenter()
                    .override(pxWidth, pxHeight)
                    .placeholder(R.drawable.bg_default)
                    .into(mItemSimilarBinding.poster);
            mItemSimilarBinding.poster.invalidate();
        }
    }
}
