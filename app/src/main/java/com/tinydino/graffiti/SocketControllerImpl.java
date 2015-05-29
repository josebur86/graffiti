package com.tinydino.graffiti;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketControllerImpl implements SocketController {
    private Socket _socket;

    private static String kMessageEvent = "new message";
    private static String kNewUserEvent = "add user";

    public SocketControllerImpl(String server, String username, Emitter.Listener messageListener) {
        try {
            _socket = IO.socket(server);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Server has an invalid URI: " + server);
        }

        _socket.on(kMessageEvent, messageListener);
        _socket.connect();
        _socket.emit(kNewUserEvent, username);
    }

    @Override
    public void destroy() {
        _socket.disconnect();
    }

    @Override
    public void sendMessage(String message) {
        _socket.emit(kMessageEvent, message);
    }
}
