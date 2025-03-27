package com.yosoyvillaa.dawselector.loader

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.commons.core.main.Loader
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import com.yosoyvillaa.dawselector.helper.MessageHelper
import com.yosoyvillaa.dawselector.helper.toMenuWrapper
import org.bukkit.Material
import xyz.xenondevs.invui.gui.structure.Structure
import xyz.xenondevs.invui.item.builder.ItemBuilder

class MenuLoader : Loader {

    @Inject @Named("config") private lateinit var config: YAMLFile
    @Inject private lateinit var messageHelper: MessageHelper

    override fun load() {
        config.get("menu", "global-items").childrenMap().forEach { (key, value) ->
            val itemBuilder = ItemBuilder(Material.valueOf(value.node("material").getString("AIR")))
                .setDisplayName(messageHelper.deserialize(value.node("display-name").getString("")).toMenuWrapper())
                .setLore(value.node("lore").getList(String::class.java, emptyList()).map { messageHelper.deserialize(it).toMenuWrapper() })
            Structure.addGlobalIngredient(key.toString()[0], itemBuilder)
        }
    }
}