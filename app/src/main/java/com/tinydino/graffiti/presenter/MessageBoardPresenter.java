package com.tinydino.graffiti.presenter;

import android.graphics.Bitmap;

import com.tinydino.graffiti.ChatMessage;
import com.tinydino.graffiti.Logger;
import com.tinydino.graffiti.MessageBoardView;
import com.tinydino.graffiti.SocketController;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageBoardPresenter {

    private final MessageBoardView _view;
    private final SocketController _socketController;

    private final String _username;
    private final String _location;

    public MessageBoardPresenter(MessageBoardView view, SocketController socketController, String username, String location) {
        _view = view;
        _socketController = socketController;
        _username = username;
        _location = location;
    }

    public void create() {
//        String server = "https://thawing-island-7364.herokuapp.com/";
//        _socketController = new SocketController(server, _username, _view.getMessageListener());
    }

    public void destroy() {
        _socketController.destroy();
    }

    public void sendMessage(String message) {
        _view.addMessageToList(new ChatMessage(_username, message, null, _location));
        _socketController.sendMessage(message);
    }

    public void sendPicture(Bitmap image) {
        _view.addMessageToList(new ChatMessage(_username, null, image, _location));
        // TODO: send the picture to the socket.
    }

    public void receiveMessage(JSONObject data) {
        String username;
        String location;
        String message;
        try {
            username = data.getString("username");
            location = "TODO"; // TODO: get location from the other users.
            message = data.getString("message");
        } catch (JSONException e) {
            new Logger().log("receiveMessage(): unable to parse JSON data");
            return;
        }

        _view.addMessageToList(new ChatMessage(username, message, null, location));
        _view.playNotificationSound();
    }
}
