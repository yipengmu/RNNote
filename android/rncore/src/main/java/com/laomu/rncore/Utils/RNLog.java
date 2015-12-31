package com.laomu.rncore.utils;

import android.util.Log;

/**
 * Created by ${yipengmu} on 15/12/31.
 */
public class RNLog {
    private static String TAG = "RNLog";

    public static void d(String s) {
        Log.d(TAG, s);
    }

    public static void e(String s) {
        Log.e(TAG, s);
    }
}
