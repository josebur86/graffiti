package com.tinydino.graffiti;

public interface MessageBoardView {
    void addMessageToList(ChatMessage message);
    void playNotificationSound();
    MessageListener getMessageListener();
}
