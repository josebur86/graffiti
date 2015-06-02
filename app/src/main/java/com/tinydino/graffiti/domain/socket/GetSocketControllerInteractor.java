package com.tinydino.graffiti.domain.socket;

import com.github.nkzawa.emitter.Emitter;
import com.tinydino.graffiti.SocketController;
import com.tinydino.graffiti.SocketControllerImpl;

import javax.inject.Inject;

public class GetSocketControllerInteractor implements GetSocketController {

    @Inject
    public GetSocketControllerInteractor() {}


    @Override
    public void execute(String server, String username, Emitter.Listener messageListener, Callback callback) {
        try {
            SocketController socketController = new SocketControllerImpl(server, username, messageListener);
            callback.onConnection(socketController);
        } catch (IllegalArgumentException e) {
            callback.onUriError();
        }
    }
}
