package com.yosoyvillaa.dawselector.listeners

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.gui.ServerSelectorGui
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var serverSelectorGui: ServerSelectorGui

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (config.get("mode").string == "LOBBY") {
            event.player.gameMode = GameMode.ADVENTURE
            serverSelectorGui.buildMenu(event.player)
        }
    }
}