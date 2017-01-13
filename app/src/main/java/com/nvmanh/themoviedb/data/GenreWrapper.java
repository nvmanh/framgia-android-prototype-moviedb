package com.nvmanh.themoviedb.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class GenreWrapper extends BaseModel {
    @SerializedName("genres")
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }
}
