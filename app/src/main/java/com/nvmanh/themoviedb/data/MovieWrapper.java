package com.nvmanh.themoviedb.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class MovieWrapper extends BaseModel {
    @SerializedName("page")
    private int mPage;
    @SerializedName("results")
    private List<Movie> mResults;
    @SerializedName("dates")
    private MovieDate mDates;
    @SerializedName("total_pages")
    private int mTotalPages;
    @SerializedName("total_results")
    private int mTotalResults;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public List<Movie> getResults() {
        return mResults;
    }

    public void setResults(List<Movie> results) {
        mResults = results;
    }

    public MovieDate getDates() {
        return mDates;
    }

    public void setDates(MovieDate dates) {
        mDates = dates;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }
}