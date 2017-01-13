package com.nvmanh.themoviedb.data;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class MovieDate extends BaseModel {
    @SerializedName("maximum")
    private Date mMaximum;
    @SerializedName("minimum")
    private Date mMinimum;

    public Date getMaximum() {
        return mMaximum;
    }

    public void setMaximum(Date maximum) {
        mMaximum = maximum;
    }

    public Date getMinimum() {
        return mMinimum;
    }

    public void setMinimum(Date minimum) {
        mMinimum = minimum;
    }
}