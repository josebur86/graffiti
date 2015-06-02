package com.tinydino.graffiti;

import android.app.Application;

import com.tinydino.graffiti.di.RootModule;

import java.util.List;

import dagger.ObjectGraph;

public class GraffitiApplication extends Application {
    private ObjectGraph _objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }

    public ObjectGraph plus(List<Object> modules) {
        if (modules == null) {
            throw new IllegalArgumentException("Modules cannot be null for plus.");
        }

        return _objectGraph.plus(modules.toArray());
    }

    private void initializeDependencyInjector() {
        _objectGraph = ObjectGraph.create(new RootModule(this));
        _objectGraph.inject(this);
        _objectGraph.injectStatics();
    }
}
