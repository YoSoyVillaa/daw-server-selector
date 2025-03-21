package com.yosoyvillaa.cloudplugin.commands

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.cloudplugin.helper.MessageHelper
import com.yosoyvillaa.cloudplugin.loader.CommandsLoader
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import me.fixeddev.commandflow.annotated.CommandClass
import me.fixeddev.commandflow.annotated.annotation.Command
import me.fixeddev.commandflow.bukkit.annotation.Sender
import org.bukkit.command.CommandSender

@Command(names = ["cloudplugin"])
class CloudPluginCommand : CommandClass {

    @Inject private lateinit var messageHelper: MessageHelper
    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject @Named("messages") private lateinit var messages: YAMLFile
    @Inject private lateinit var commandsLoader: CommandsLoader

    @Command(names = [""])
    fun onHelpCommand(@Sender commandSender: CommandSender) =
        commandSender.sendMessage(messageHelper.deserialize(emptyMap(), "commands", "main", "help"))

    @Command(names = ["reload"], permission = "cloudplugin.admin")
    fun onReloadCommand(@Sender commandSender: CommandSender) {
        config.reload()
        messages.reload()
        commandsLoader.reload()
        commandSender.sendMessage(messageHelper.deserialize(emptyMap(), "commands", "main", "reload"))
    }
}