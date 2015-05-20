package com.tinydino.graffiti;

import android.graphics.Bitmap;

/**
 * Created by jetters on 5/17/15.
 */
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
