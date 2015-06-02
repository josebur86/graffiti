package com.tinydino.graffiti.domain.socket;

import dagger.Module;
import dagger.Provides;

@Module(library = true, complete = false)
public final class SocketModule {
    @Provides
    GetSocketController provideGetSocketControllerInteractor(GetSocketControllerInteractor interactor) {
        return interactor;
    }
}
