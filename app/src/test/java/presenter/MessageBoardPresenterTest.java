package com.tinydino.graffiti.test.presenter;

import com.tinydino.graffiti.presenter.MessageBoardPresenter;

import org.junit.Before;
import org.junit.Test;

import fakes.FakeMessageBoardView;
import fakes.FakeSocketController;

import static org.junit.Assert.*;

public class MessageBoardPresenterTest {

    FakeMessageBoardView _view;
    FakeSocketController _socketController;

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

        assertEquals(message, _view.getMessages().get(0).Message);
    }
}