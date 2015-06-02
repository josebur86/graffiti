package com.tinydino.test.fakes;

import com.github.nkzawa.emitter.Emitter;
import com.tinydino.graffiti.domain.chat.ChatMessage;
import com.tinydino.graffiti.ui.presenter.MessageBoardPresenter;

import java.util.ArrayList;
import java.util.List;

public class FakeMessageBoardView implements MessageBoardPresenter.View {

    private final List<ChatMessage> _messages;

    public FakeMessageBoardView() {
        _messages = new ArrayList<>();
    }

    public List<ChatMessage> getMessages() {
        return _messages;
    }

    @Override
    public void addMessageToList(ChatMessage message) {
        _messages.add(message);
    }

    @Override
    public void playNotificationSound() {

    }

    @Override
    public String getUsername() {
        return "FakeUsername";
    }

    @Override
    public String getLocation() {
        return "FakeLocation";
    }

    @Override
    public Emitter.Listener getMessageListener() {
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        };
    }
}
