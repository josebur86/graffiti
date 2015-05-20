package com.tinydino.graffiti;

import android.util.Log;

import com.example.drew.graffiti.BuildConfig;

public class Logger {

    private static String kTag = "graffiti";

    public void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(kTag, message);
        }
    }
}
