package com.tinydino.test.fakes;

import com.github.nkzawa.emitter.Emitter;
import com.tinydino.graffiti.domain.socket.GetSocketController;

public class FakeGetSocketController implements GetSocketController{

    private final FakeSocketController _controller;

    public FakeGetSocketController(FakeSocketController socketController) {
        _controller = socketController;
    }

    @Override
    public void execute(String server, String username, Emitter.Listener messageListener, Callback callback) {
        callback.onConnection(_controller);
    }
}
