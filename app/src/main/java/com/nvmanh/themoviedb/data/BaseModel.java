package com.nvmanh.themoviedb.data;

import android.widget.Checkable;
import java.io.Serializable;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class BaseModel implements Serializable, Checkable {
    private boolean checked;

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        checked = !checked;
    }
}
