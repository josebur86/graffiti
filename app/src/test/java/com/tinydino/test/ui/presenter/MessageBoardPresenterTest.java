package com.tinydino.test.ui.presenter;

import com.tinydino.graffiti.ChatMessage;
import com.tinydino.graffiti.ui.presenter.MessageBoardPresenter;
import com.tinydino.test.fakes.FakeMessageBoardView;
import com.tinydino.test.fakes.FakeSocketController;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

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
        MessageBoardPresenter presenter = new MessageBoardPresenter(_socketController, "Test User", "Test Location");
        presenter.setView(_view);

        String message = "Fake Message";
        presenter.sendMessage(message);

        assertEquals(1, _view.getMessages().size());
        ChatMessage chatMessage = _view.getMessages().get(0);
        assertNull(chatMessage.Image);
        assertEquals(message, chatMessage.Message);
    }

    @Test
    public void sendPictureAddNewPictureToView() {
        MessageBoardPresenter presenter = new MessageBoardPresenter(_socketController, "Test User", "Test Location");
        presenter.setView(_view);
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