package com.example.maks.infidemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by maks on 27/11/17.
 */

public class NetworkUtil {
    public static boolean isInternetConnectionAvailable(Context context) {

        // check network connection
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }
}
