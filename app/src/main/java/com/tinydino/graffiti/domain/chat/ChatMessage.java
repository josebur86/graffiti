package com.tinydino.graffiti.domain.chat;

import java.nio.ByteBuffer;

public class ChatMessage {
    public String UserName;
    public String Message;
    public ByteBuffer Image;
    public String UserLocation;

    public ChatMessage(String userName, String message, ByteBuffer image, String userLocation) {
        UserName = userName;
        Message = message;
        Image = image;
        UserLocation = userLocation;
    }
}
