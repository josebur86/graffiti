package com.tinydino.graffiti.domain.socket;

import com.github.nkzawa.emitter.Emitter; // TODO: find a way to decouple this dependency.
import com.tinydino.graffiti.SocketController;

public interface GetSocketController {
    interface Callback {
        void onConnection(SocketController socketController);
        void onUriError();
    }

    void execute(final String server, final String username,
                 Emitter.Listener messageListener, final Callback callback);
}
