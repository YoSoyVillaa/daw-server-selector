package com.yosoyvillaa.dawselector.loader

import com.google.inject.Inject
import com.yosoyvillaa.dawselector.DawServerSelector
import com.yosoyvillaa.commons.core.main.Loader
import com.yosoyvillaa.dawselector.listeners.PlayerJoinListener
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class ListenersLoader : Loader {

    @Inject private lateinit var cloudPlugin: DawServerSelector

    @Inject private lateinit var playerJoinListener: PlayerJoinListener

    override fun load() {
        registerListener(playerJoinListener)
    }

    private fun registerListener(vararg listeners: Listener) = listeners.forEach { listener ->
        Bukkit.getServer().pluginManager.registerEvents(listener, cloudPlugin)
    }
}