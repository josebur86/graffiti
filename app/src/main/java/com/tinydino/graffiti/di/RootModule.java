package com.tinydino.graffiti.di;

import android.content.Context;

import com.tinydino.graffiti.GraffitiApplication;
import com.tinydino.graffiti.domain.socket.SocketModule;

import dagger.Module;

@Module(
    includes = {
        SocketModule.class
    },
    injects = {
        GraffitiApplication.class
    }, library = true)
public final class RootModule {
    private final Context _context;

    public RootModule(Context context) {
        _context = context;
    }
}
