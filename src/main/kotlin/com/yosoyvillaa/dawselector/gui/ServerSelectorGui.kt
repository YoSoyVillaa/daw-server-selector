package com.yosoyvillaa.dawselector.gui

import com.google.inject.Inject
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.gui.items.CloseItem
import com.yosoyvillaa.dawselector.gui.items.NextPageItem
import com.yosoyvillaa.dawselector.gui.items.PreviousPageItem
import com.yosoyvillaa.dawselector.gui.items.ServerItem
import com.yosoyvillaa.dawselector.helper.MessageHelper
import com.yosoyvillaa.dawselector.helper.toMenuWrapper
import com.yosoyvillaa.dawselector.manager.ServersManager
import com.yosoyvillaa.dawselector.service.ServiceLocator
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.window.Window

@Singleton
class ServerSelectorGui {

    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var serversManager: ServersManager
    @Inject private lateinit var messageHelper: MessageHelper

    fun buildMenu(player: Player) {
        val closeItem = ServiceLocator.getService(CloseItem::class.java)
        val nextItem = ServiceLocator.getService(NextPageItem::class.java)
        val previousItem = ServiceLocator.getService(PreviousPageItem::class.java)

        val servers = serversManager.getServers().sortedBy { it.name }.map { server ->
            val item = ServiceLocator.getService(ServerItem::class.java)
            item.setServer(server)
            item
        }

        val gui = PagedGui.items()
            .setStructure(*config.get("menu", "structure").getList(String::class.java, emptyList()).toTypedArray())
            .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient('<', previousItem)
            .addIngredient('>', nextItem)
            .addIngredient('c', closeItem)
            .setContent(servers)
            .build()

        Window.single()
            .setTitle(messageHelper.deserialize(config.get("menu", "title").getString("Selecciona un servidor")).toMenuWrapper())
            .setGui(gui)
            .setCloseable(config.get("mode").string == "SERVER")
            .open(player)
    }
}