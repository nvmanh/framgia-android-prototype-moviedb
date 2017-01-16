package com.nvmanh.themoviedb.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.nvmanh.themoviedb.R;
import com.nvmanh.themoviedb.data.Movie;
import com.nvmanh.themoviedb.databinding.ItemMoviesBinding;
import com.nvmanh.themoviedb.utils.Common;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mInflater;
    private MoviesContract.Presenter mPresenter;

    public void add(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) return;
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) mInflater = LayoutInflater.from(parent.getContext());
        ItemMoviesBinding binding = ItemMoviesBinding.inflate(mInflater, parent, false);
        return new MoviesHolder(binding);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        holder.bind(getMovies().get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setPresenter(MoviesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    class MoviesHolder extends RecyclerView.ViewHolder {
        private ItemMoviesBinding mItemMoviesBinding;

        public MoviesHolder(ItemMoviesBinding binding) {
            super(binding.getRoot());
            mItemMoviesBinding = binding;
        }

        void bind(Movie movie) {
            mItemMoviesBinding.setMovies(movie);
            mItemMoviesBinding.setPresenter(mPresenter);
            Glide.with(itemView.getContext())
                    .load(Common.getImageMovie(movie.getPosterPath()))
                    .fitCenter()
                    .placeholder(R.drawable.bg_default)
                    .into(mItemMoviesBinding.poster);
        }
    }
}
