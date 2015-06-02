package com.tinydino.graffiti.ui.util;

import android.app.Activity;

import com.tinydino.graffiti.ui.presenter.MessageBoardPresenter;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

public class MessageListener implements Emitter.Listener {

    private Activity _activity;
    private MessageBoardPresenter _presenter; // TODO: this should not be a concrete presenter.

    public MessageListener(Activity activity, MessageBoardPresenter presenter) {
        _activity = activity;
        _presenter = presenter;
    }

    @Override
    public void call(final Object... objects) {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject data = (JSONObject)objects[0];
                _presenter.receiveMessage(data);
            }
        });
    }
}
