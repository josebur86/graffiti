package com.tinydino.test.ui.presenter;

import com.tinydino.graffiti.domain.chat.ChatMessage;
import com.tinydino.graffiti.domain.socket.GetSocketController;
import com.tinydino.graffiti.ui.presenter.MessageBoardPresenter;
import com.tinydino.test.fakes.FakeGetSocketController;
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
        GetSocketController getSocketController = new FakeGetSocketController(_socketController);
        MessageBoardPresenter presenter = new MessageBoardPresenter(getSocketController);
        presenter.setView(_view);

        String message = "Fake Message";
        presenter.sendMessage(message);

        assertEquals(1, _view.getMessages().size());
        ChatMessage chatMessage = _view.getMessages().get(0);
        assertNull(chatMessage.Image);
        assertEquals(message, chatMessage.Message);
    }

    @Test
    public void sendMessageAddsNewMessageToSocketController() {
        FakeGetSocketController getSocketController = new FakeGetSocketController(_socketController);
        MessageBoardPresenter presenter = new MessageBoardPresenter(getSocketController);
        presenter.setView(_view);

        String message = "Fake message";
        presenter.sendMessage(message);

        assertEquals(1, _socketController.getMessages().size());
        assertEquals(message, _socketController.getMessages().get(0));
    }

    @Test
    public void sendPictureAddNewPictureToView() {
        GetSocketController getSocketController = new FakeGetSocketController(_socketController);
        MessageBoardPresenter presenter = new MessageBoardPresenter(getSocketController);
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