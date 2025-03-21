package com.yosoyvillaa.cloudplugin.loader

import com.google.inject.Inject
import com.yosoyvillaa.cloudplugin.CloudPlugin
import com.yosoyvillaa.commons.core.main.Loader
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class ListenersLoader : Loader {

    @Inject private lateinit var cloudPlugin: CloudPlugin

    override fun load() {
        registerListener()
    }

    private fun registerListener(vararg listeners: Listener) = listeners.forEach { listener ->
        Bukkit.getServer().pluginManager.registerEvents(listener, cloudPlugin)
    }
}