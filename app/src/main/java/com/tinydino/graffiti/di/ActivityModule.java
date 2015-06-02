package com.tinydino.graffiti.di;

import android.app.Activity;

import dagger.Module;

@Module
public final class ActivityModule {
    private final Activity _activity;

    public ActivityModule(Activity activity) {
        _activity = activity;
    }
}
