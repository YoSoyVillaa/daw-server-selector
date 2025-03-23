package com.yosoyvillaa.dawselector.module

import com.google.inject.AbstractModule
import com.yosoyvillaa.dawselector.DawServerSelector
import com.yosoyvillaa.dawselector.service.MainService
import com.yosoyvillaa.commons.core.guice.build
import com.yosoyvillaa.commons.core.main.Service
import com.yosoyvillaa.commons.core.main.configuration.FileMatcher
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import org.bukkit.plugin.Plugin

class MainModule(private val cloudPlugin: DawServerSelector) : AbstractModule() {

    override fun configure() {
        bind(DawServerSelector::class.java).toInstance(cloudPlugin)
        bind(Plugin::class.java).to(DawServerSelector::class.java)

        val fileMatcher = FileMatcher()
            .bind("config", YAMLFile(this, "config.yml", cloudPlugin.dataFolder.toPath()))
            .bind("messages", YAMLFile(this, "messages.yml", cloudPlugin.dataFolder.toPath()))
        install(fileMatcher.build())

        bind(Service::class.java).to(MainService::class.java)
    }

}
