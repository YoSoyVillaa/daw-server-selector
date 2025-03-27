package com.yosoyvillaa.dawselector.gui.items

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.helper.MenuItemHelper
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.impl.controlitem.PageItem

class PreviousPageItem : PageItem(false) {

    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var menuItemHelper: MenuItemHelper

    override fun getItemProvider(gui: PagedGui<*>): ItemProvider {
        return if (gui.hasPreviousPage()) {
            menuItemHelper.getItem(config, "menu", "items", "previous")
        } else {
            menuItemHelper.getItem(config, "menu", "global-items", "#")
        }
    }
}