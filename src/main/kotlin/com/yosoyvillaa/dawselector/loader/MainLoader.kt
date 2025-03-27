package com.yosoyvillaa.dawselector.loader

import com.google.inject.Inject
import com.yosoyvillaa.commons.core.main.Loader
import com.yosoyvillaa.dawselector.DawServerSelector
import com.yosoyvillaa.dawselector.manager.ServersManager

class MainLoader : Loader {

    @Inject private lateinit var dawServerSelector: DawServerSelector
    @Inject private lateinit var serversManager: ServersManager
    @Inject private lateinit var commandsLoader: CommandsLoader
    @Inject private lateinit var listenersLoader: ListenersLoader
    @Inject private lateinit var menuLoader: MenuLoader

    override fun load() {
        serversManager.loadServers()
        commandsLoader.load()
        listenersLoader.load()
        menuLoader.load()
        dawServerSelector.server.messenger.registerOutgoingPluginChannel(dawServerSelector, "BungeeCord");
    }
}