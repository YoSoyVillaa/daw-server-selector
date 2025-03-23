package com.yosoyvillaa.dawselector.loader

import com.google.inject.Inject
import com.yosoyvillaa.commons.core.main.Loader

class MainLoader : Loader {

    @Inject private lateinit var commandsLoader: CommandsLoader
    @Inject private lateinit var listenersLoader: ListenersLoader

    override fun load() {
        commandsLoader.load()
        listenersLoader.load()
    }
}