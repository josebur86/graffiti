package com.tinydino.graffiti.test.presenter;

import com.tinydino.graffiti.ChatMessage;
import com.tinydino.graffiti.presenter.MessageBoardPresenter;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import fakes.FakeMessageBoardView;
import fakes.FakeSocketController;

import static org.junit.Assert.*;

public class MessageBoardPresenterTest {

    private FakeMessageBoardView _view;
    private FakeSocketController _socketController;

    @Before
    public void setup()
    {
        _view = new FakeMessageBoardView();
        _socketController = new FakeSocketController();
    }

    @Test
    public void sendMessageAddsNewMessageToView() {
        MessageBoardPresenter presenter = new MessageBoardPresenter(_view, _socketController, "Test User", "Test Location");

        String message = "Fake Message";
        presenter.sendMessage(message);

        assertEquals(1, _view.getMessages().size());
        ChatMessage chatMessage = _view.getMessages().get(0);
        assertNull(chatMessage.Image);
        assertEquals(message, chatMessage.Message);
    }

    @Test
    public void sendPictureAddNewPictureToView() {
        MessageBoardPresenter presenter = new MessageBoardPresenter(_view, _socketController, "Test User", "Test Location");
        String _snoop =
                "R0lGODlhYAL+AvcAAAAAAAEBAQICAgMDAwQEBAUFBQYGBgcHBwgICAkJCQoKCgsLCww";
        ByteBuffer imageBuffer = ByteBuffer.wrap(_snoop.getBytes());
        presenter.sendPicture(imageBuffer);

        assertEquals(1, _view.getMessages().size());
        ChatMessage chatMessage = _view.getMessages().get(0);
        assertNull(chatMessage.Message);
        assertEquals(_snoop, new String(chatMessage.Image.array()));
    }
}