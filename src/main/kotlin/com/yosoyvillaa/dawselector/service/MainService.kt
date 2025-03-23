package com.yosoyvillaa.dawselector.service

import com.google.inject.Inject
import com.yosoyvillaa.dawselector.loader.MainLoader
import com.yosoyvillaa.commons.core.main.Service

class MainService : Service {

    @Inject private lateinit var mainLoader: MainLoader

    override fun start() = mainLoader.load()

    override fun stop() {
        // Stop procedure if required
    }
}