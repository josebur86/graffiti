package com.tinydino.graffiti;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketController {
    private Socket _socket;

    private static String kMessageEvent = "new message";
    private static String kNewUserEvent = "add user";

    public SocketController(String server, String username, MessageListener messageListener) {
        try {
            _socket = IO.socket(server);
        } catch (URISyntaxException e) {
            new Logger().log(e.toString());
        }

        _socket.on(kMessageEvent, messageListener);
        _socket.connect();
        _socket.emit(kNewUserEvent, username);
    }

    public void destroy() {
        _socket.disconnect();
    }

    public void sendMessage(String message) {
        _socket.emit(kMessageEvent, message);
    }
}
