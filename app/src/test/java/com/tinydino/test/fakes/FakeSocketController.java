package com.tinydino.test.fakes;

import com.tinydino.graffiti.SocketController;

import java.util.ArrayList;
import java.util.List;

public class FakeSocketController implements SocketController {

    private List<String> _messages = new ArrayList<>();

    @Override
    public void sendMessage(String message) {
        _messages.add(message);
    }

    @Override
    public void destroy() {

    }

    public List<String> getMessages() {
        return _messages;
    }
}
