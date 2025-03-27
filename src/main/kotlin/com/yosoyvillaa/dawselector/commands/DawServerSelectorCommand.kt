package com.yosoyvillaa.dawselector.commands

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.dawselector.helper.MessageHelper
import com.yosoyvillaa.dawselector.loader.CommandsLoader
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.gui.ServerSelectorGui
import com.yosoyvillaa.dawselector.manager.ServersManager
import me.fixeddev.commandflow.annotated.CommandClass
import me.fixeddev.commandflow.annotated.annotation.Command
import me.fixeddev.commandflow.bukkit.annotation.Sender
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command(names = ["dawselector"])
class DawServerSelectorCommand : CommandClass {

    @Inject private lateinit var messageHelper: MessageHelper
    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject @Named("messages") private lateinit var messages: YAMLFile
    @Inject private lateinit var commandsLoader: CommandsLoader
    @Inject private lateinit var serverSelectorGui: ServerSelectorGui
    @Inject private lateinit var serversManager: ServersManager

    @Command(names = [""])
    fun onDawSelectorCommand(@Sender player: Player) {
        serverSelectorGui.buildMenu(player)
    }

    @Command(names = ["reload"], permission = "dawselector.admin")
    fun onReloadCommand(@Sender commandSender: CommandSender) {
        config.reload()
        messages.reload()
        commandsLoader.reload()
        serversManager.loadServers()
        commandSender.sendMessage(messageHelper.deserialize(emptyMap(), "commands", "main", "reload"))
    }
}