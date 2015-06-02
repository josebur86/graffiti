package com.tinydino.graffiti.ui.presenter;

import com.tinydino.graffiti.domain.socket.SocketModule;
import com.tinydino.graffiti.ui.activity.MessageBoardActivity;

import dagger.Module;

@Module(includes = {
    SocketModule.class
}, injects = {
    MessageBoardActivity.class
})
public final class UIModule {
}
