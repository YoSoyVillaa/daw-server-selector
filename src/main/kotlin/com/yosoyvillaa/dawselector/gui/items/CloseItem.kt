package com.yosoyvillaa.dawselector.gui.items

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.helper.MenuItemHelper
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.impl.AbstractItem

class CloseItem : AbstractItem() {

    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var menuItemHelper: MenuItemHelper

    override fun getItemProvider(): ItemProvider {
        return if (config.get("mode").string == "SERVER") {
            menuItemHelper.getItem(config, "menu", "items", "close")
        } else {
            menuItemHelper.getItem(config, "menu", "items", "disconnect")
        }
    }

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        if (config.get("mode").string == "SERVER") {
            player.closeInventory()
        } else {
            player.kick()
        }
    }
}