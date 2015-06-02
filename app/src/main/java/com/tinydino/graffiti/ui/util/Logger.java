package com.tinydino.graffiti.ui.util;

import android.util.Log;

import com.tinydino.graffiti.BuildConfig;

public class Logger {

    private static String kTag = "graffiti";

    public void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(kTag, message);
        }
    }
}
