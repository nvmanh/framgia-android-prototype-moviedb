package com.nvmanh.themoviedb.data;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class Genre extends BaseModel {
    @DatabaseField(id = true)
    @SerializedName("id")
    private int mId;
    @DatabaseField
    @SerializedName("name")
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}