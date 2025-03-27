package com.yosoyvillaa.dawselector

import com.google.inject.Guice
import com.yosoyvillaa.dawselector.module.MainModule
import com.yosoyvillaa.commons.core.main.Service
import com.yosoyvillaa.dawselector.service.ServiceLocator
import org.bukkit.plugin.java.JavaPlugin

class DawServerSelector : JavaPlugin() {

    private lateinit var mainService: Service

    override fun onEnable() {
        val injector = Guice.createInjector(MainModule(this))
        injector.injectMembers(this)
        ServiceLocator.setInjector(injector)

        mainService = injector.getInstance(Service::class.java)
        mainService.start()
    }

    override fun onDisable() {
        mainService.stop()
    }
}
