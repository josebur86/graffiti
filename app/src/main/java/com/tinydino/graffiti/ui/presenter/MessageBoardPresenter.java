package com.tinydino.graffiti.ui.presenter;

import com.github.nkzawa.emitter.Emitter;
import com.tinydino.graffiti.domain.chat.ChatMessage;
import com.tinydino.graffiti.ui.util.Logger;
import com.tinydino.graffiti.domain.socket.SocketController;
import com.tinydino.graffiti.domain.socket.GetSocketController;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;

import javax.inject.Inject;

public class MessageBoardPresenter {

    private View _view;
    private SocketController _socketController;
    private GetSocketController _getSocketController;

    private String _username;
    private String _location;

    @Inject
    public MessageBoardPresenter(GetSocketController getSocketController) {
        _getSocketController = getSocketController;
    }

    public void setView(View view) {
        _view = view;

        _username = _view.getUsername();
        _location = _view.getLocation();
        _getSocketController.execute("https://thawing-island-7364.herokuapp.com/",
                _view.getUsername(), _view.getMessageListener(), new GetSocketController.Callback() {
                    @Override
                    public void onConnection(SocketController socketController) {
                        _socketController = socketController;
                    }

                    @Override
                    public void onUriError() {
                        // TODO: Send a toast that to inform the user.
                    }
                });
    }

    public void destroy() {
        _socketController.destroy();
    }

    public void sendMessage(String message) {
        _view.addMessageToList(new ChatMessage(_username, message, null, _location));
        _socketController.sendMessage(message);
    }

    public void sendPicture(ByteBuffer image) {
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

    public interface View {
        void addMessageToList(ChatMessage message);
        void playNotificationSound();
        String getUsername();
        String getLocation();
        Emitter.Listener getMessageListener();
    }
}
