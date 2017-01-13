package com.nvmanh.themoviedb.data;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.nvmanh.themoviedb.data.source.local.MoviesPersistenceContract;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */
public class Movie extends BaseModel {
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_POSTER_PATH)
    private String mPosterPath;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_ADULT)
    private boolean mAdult;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_OVERVIEW)
    private String mOverview;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_RELEASE_DATE)
    private String mReleaseDate;
    //@DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_GENRE_IDS)
    private int[] mGenreIds;
    @DatabaseField(id = true)
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_ID)
    private int mId;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE)
    private String mOriginalTitle;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE)
    private String mOriginalLanguage;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_TITLE)
    private String mTitle;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH)
    private String mBackdropPath;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_POPULARITY)
    private float mPopularity;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_VOTE_COUNT)
    private int mVoteCount;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_VIDEO)
    private boolean mVideo;
    @DatabaseField
    @SerializedName(MoviesPersistenceContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE)
    private float mVoteAverage;
    private boolean favorite;
    @DatabaseField
    private String mGenreList;

    public Movie() {
        super();
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        mAdult = adult;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(int[] genreIds) {
        mGenreIds = genreIds;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        mPopularity = popularity;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        mVoteCount = voteCount;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        mVideo = video;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getGenreList() {
        return mGenreList;
    }

    public void setGenreList(String genreList) {
        mGenreList = genreList;
    }
}
