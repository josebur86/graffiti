package com.tinydino.graffiti;

import android.graphics.Bitmap;

public class ChatMessage {
    public String UserName;
    public String Message;
    public Bitmap Image;
    public String UserLocation;

    public ChatMessage(String userName, String message, Bitmap image, String userLocation) {
        UserName = userName;
        Message = message;
        Image = image;
        UserLocation = userLocation;
    }
    public ChatMessage() {}

}
