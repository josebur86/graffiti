package com.tinydino.test.domain.socket;

import com.github.nkzawa.emitter.Emitter;
import com.tinydino.graffiti.domain.socket.SocketController;
import com.tinydino.graffiti.domain.socket.GetSocketController;
import com.tinydino.graffiti.domain.socket.GetSocketControllerInteractor;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GetSocketControllerInteractorTest {

    private class FakeGetSocketControllerCallback implements GetSocketController.Callback {
        public boolean InvalidUriSignaled = false;
        public boolean ConnectionSignaled = false;
        public SocketController SocketController = null;

        @Override
        public void onConnection(SocketController socketController) {
            ConnectionSignaled = true;
            this.SocketController = socketController;
        }

        @Override
        public void onUriError() {
            InvalidUriSignaled = true;
        }
    }

    private class FakeMessageListener implements Emitter.Listener {

        @Override
        public void call(Object... args) {

        }
    }

    @Test
    public void execute_invalidUri_SignalsInvalidUri() {
        GetSocketController getSocketController = new GetSocketControllerInteractor();
        FakeGetSocketControllerCallback callback = new FakeGetSocketControllerCallback();

        getSocketController.execute("Invalid URI", "user", new FakeMessageListener(), callback);
        assertTrue(callback.InvalidUriSignaled);
    }

    @Test
    public void execute_validUri_SignalsConnectionAndSocketIsValid() {
        GetSocketController getSocketController = new GetSocketControllerInteractor();
        FakeGetSocketControllerCallback callback = new FakeGetSocketControllerCallback();

        getSocketController.execute("https://example.com", "user", new FakeMessageListener(), callback);
        assertTrue(callback.ConnectionSignaled);
        assertNotNull(callback.SocketController);
    }
}
