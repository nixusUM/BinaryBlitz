package com.examle.binaryblitz.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.examle.binaryblitz.application.App;

public class NetworkUtils {

    public static boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
