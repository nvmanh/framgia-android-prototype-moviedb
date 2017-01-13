package com.nvmanh.themoviedb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.nvmanh.themoviedb.App;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public class InternetUtil {
    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivity =
                (ConnectivityManager) App.self().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null || connectivity.getAllNetworkInfo() == null) return false;
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        for (NetworkInfo anInfo : info) {
            if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
