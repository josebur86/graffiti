package com.example.drew.graffiti;

import android.util.Log;

public class Logger {

    private static String kTag = "graffiti";

    public void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(kTag, message);
        }
    }
}
