package com.tinydino.graffiti;

import java.util.List;

public interface MessageBoardView {
    void setMessages(List<ChatMessage> messages);
    void onAddMessage();
    void playNotificationSound();
    MessageListener getMessageListener();
}