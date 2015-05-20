package com.tinydino.graffiti.presenter;

import android.graphics.Bitmap;

import com.tinydino.graffiti.ChatMessage;
import com.tinydino.graffiti.Logger;
import com.tinydino.graffiti.MessageBoardView;
import com.tinydino.graffiti.SocketController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageBoardPresenter {

    private MessageBoardView _view;
    private SocketController _socketController;

    private String _username;
    private String _location;

    private List<ChatMessage> _messages;

    public MessageBoardPresenter(MessageBoardView view, String username, String location) {
        _view = view;
        _username = username;
        _location = location;

        _messages = new ArrayList<ChatMessage>();
    }

    public void create() {
        _view.setMessages(_messages);

        String server = "https://thawing-island-7364.herokuapp.com/";
        _socketController = new SocketController(server, _username, _view.getMessageListener());
    }

    public void destroy() {
        _socketController.destroy();
    }

    public void sendMessage(String message) {
        _messages.add(new ChatMessage(_username, message, null, _location));
        _view.onAddMessage();
        _socketController.sendMessage(message);
    }

    public void sendPicture(Bitmap image) {
        _messages.add(new ChatMessage(_username, null, image, _location));
        _view.onAddMessage();
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

        _messages.add(new ChatMessage(username, message, null, location));
        _view.onAddMessage();
        _view.playNotificationSound();
    }
}
