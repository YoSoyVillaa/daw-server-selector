package com.yosoyvillaa.dawselector.manager

import com.google.inject.Inject
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.objects.DawServer

@Singleton
class ServersManager {

    @Inject @Named("config") private lateinit var config: YAMLFile

    private val servers = mutableListOf<DawServer>()

    fun loadServers() {
        servers.clear()
        config.get("servers").getList(DawServer::class.java, emptyList()).forEach { server ->
            servers.add(server)
        }
    }

    fun getServers() = servers.toList()
}