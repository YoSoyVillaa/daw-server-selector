package com.yosoyvillaa.dawselector.gui.items

import com.google.common.io.ByteStreams
import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.DawServerSelector
import com.yosoyvillaa.dawselector.helper.MessageHelper
import com.yosoyvillaa.dawselector.helper.toMenuWrapper
import com.yosoyvillaa.dawselector.objects.CharItems
import com.yosoyvillaa.dawselector.objects.DawServer
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.SkullBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem

class ServerItem : AbstractItem() {

    @Inject private lateinit var dawServerSelector: DawServerSelector
    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var messageHelper: MessageHelper

    private lateinit var server: DawServer

    override fun getItemProvider(): ItemProvider {
        val initial = server.name.uppercase()[0]
        val replacers = mapOf("%name%" to server.name, "%host%" to server.host, "%id%" to server.serverId)

        var lore = config.get("menu", "items", "server", "lore").getList(String::class.java, emptyList())
        lore.forEachIndexed { index, string ->
            var replaced = string
            for ((key, value) in replacers) replaced = replaced.replace(key, value.toString())
            lore[index] = PlaceholderAPI.setPlaceholders(null, replaced)
        }
        return SkullBuilder(SkullBuilder.HeadTexture(CharItems.CHAR_MAP[initial]!!.random()))
            .setDisplayName(messageHelper.deserialize(config, replacers, "menu", "items", "server", "display-name").toMenuWrapper())
            .setLore(lore.map { messageHelper.deserialize(it).toMenuWrapper() })
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        val out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server.serverId);
        player.sendPluginMessage(dawServerSelector, "BungeeCord", out.toByteArray());
    }

    fun setServer(server: DawServer) {
        this.server = server
    }
}