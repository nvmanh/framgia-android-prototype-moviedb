package com.nvmanh.themoviedb.main;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.nvmanh.themoviedb.R;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 13/01/2017.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        ProgressBar mProgressBar = new ProgressBar(context);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        mProgressBar.setIndeterminate(true);
        setContentView(mProgressBar);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }
}
