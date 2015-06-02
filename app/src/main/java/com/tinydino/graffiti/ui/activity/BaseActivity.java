package com.tinydino.graffiti.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tinydino.graffiti.GraffitiApplication;
import com.tinydino.graffiti.di.ActivityModule;

import java.util.List;

import dagger.ObjectGraph;

public abstract class BaseActivity extends Activity {
    private ObjectGraph _activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    public void inject(Object object) {
        _activityGraph.inject(object);
    }

    protected abstract List<Object> getModules();

    private void injectDependencies() {
        GraffitiApplication application = (GraffitiApplication) getApplication();
        List<Object> activityModules = getModules();
        activityModules.add(new ActivityModule(this));
        _activityGraph = application.plus(activityModules);
        inject(this);
    }
}
