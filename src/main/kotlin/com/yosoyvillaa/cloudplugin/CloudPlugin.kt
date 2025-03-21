package com.yosoyvillaa.cloudplugin

import com.google.inject.Guice
import com.yosoyvillaa.cloudplugin.module.MainModule
import com.yosoyvillaa.commons.core.main.Service
import org.bukkit.plugin.java.JavaPlugin

class CloudPlugin : JavaPlugin() {

    private lateinit var mainService: Service

    override fun onEnable() {
        val injector = Guice.createInjector(MainModule(this))
        injector.injectMembers(this)

        mainService = injector.getInstance(Service::class.java)
        mainService.start()
    }

    override fun onDisable() {
        mainService.stop()
    }
}
