package com.yosoyvillaa.cloudplugin.commands.provider

import com.google.inject.Inject
import com.yosoyvillaa.cloudplugin.helper.MessageHelper
import me.fixeddev.commandflow.bukkit.BukkitDefaultTranslationProvider
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

class CustomTranslationProvider : BukkitDefaultTranslationProvider() {

    @Inject private lateinit var messageHelper: MessageHelper

    private val componentSerializer = LegacyComponentSerializer
        .builder()
        .character(LegacyComponentSerializer.AMPERSAND_CHAR)
        .hexColors()
        .build()

    fun load(): CustomTranslationProvider {
        translations["command.no-permission"] = colorize("errors", "no_permission")
        translations["player.offline"] = colorize("errors", "player_not_found")
        return this
    }

    private fun colorize(vararg path: String) = componentSerializer.serialize(messageHelper.deserialize(emptyMap(),  *path))
}