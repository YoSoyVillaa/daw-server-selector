package com.yosoyvillaa.dawselector.service

import com.google.inject.Injector

object ServiceLocator {
    private lateinit var injector: Injector

    fun setInjector(inj: Injector) {
        injector = inj
    }

    fun <T> getService(serviceClass: Class<T>): T {
        return injector.getInstance(serviceClass)
    }
}