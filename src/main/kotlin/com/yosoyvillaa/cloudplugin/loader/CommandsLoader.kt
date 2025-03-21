package com.yosoyvillaa.cloudplugin.loader

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Singleton
import com.yosoyvillaa.cloudplugin.commands.CloudPluginCommand
import com.yosoyvillaa.cloudplugin.commands.builder.CustomUsageBuilder
import com.yosoyvillaa.cloudplugin.commands.provider.CustomTranslationProvider
import com.yosoyvillaa.commons.core.main.Loader
import me.fixeddev.commandflow.CommandManager
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl
import me.fixeddev.commandflow.annotated.CommandClass
import me.fixeddev.commandflow.annotated.SubCommandInstanceCreator
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilder
import me.fixeddev.commandflow.annotated.part.SimplePartInjector
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule
import me.fixeddev.commandflow.bukkit.BukkitCommandManager
import me.fixeddev.commandflow.bukkit.factory.BukkitModule

@Singleton
class CommandsLoader : Loader {

    @Inject private lateinit var injector: Injector
    @Inject private lateinit var customTranslationProvider: CustomTranslationProvider
    @Inject private lateinit var customUsageBuilder: CustomUsageBuilder

    @Inject private lateinit var cloudPluginCommand: CloudPluginCommand

    private lateinit var commandManager: CommandManager

    override fun load() {
        val partInjector = SimplePartInjector()
        partInjector.install(DefaultsModule())
        partInjector.install(BukkitModule())

        val builder = AnnotatedCommandBuilder.create(partInjector)
        val instanceCreator = SubCommandInstanceCreator { clazz, _ ->  injector.getInstance(clazz)}

        val annotatedCommandTreeBuilder = AnnotatedCommandTreeBuilderImpl(builder, instanceCreator)
        commandManager = BukkitCommandManager("cloudplugin")

        commandManager.translator.setProvider(customTranslationProvider.load())
        commandManager.usageBuilder = customUsageBuilder

        registerCommands(annotatedCommandTreeBuilder, commandManager, cloudPluginCommand)
    }

    fun reload() = commandManager.translator.setProvider(customTranslationProvider.load())

    private fun registerCommands(commandBuilder: AnnotatedCommandTreeBuilder, commandManager: CommandManager, vararg commands: CommandClass) {
        for (command in commands) commandManager.registerCommands(commandBuilder.fromClass(command))
    }
}