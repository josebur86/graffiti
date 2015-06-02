package com.tinydino.graffiti.domain.socket;

public interface SocketController {
    void sendMessage(String message);
    void destroy();
}
