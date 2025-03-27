package com.yosoyvillaa.dawselector.helper

import com.google.inject.Inject
import com.google.inject.Singleton
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.contains
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.builder.SkullBuilder
import java.util.UUID

/**
 * @author YoSoyVilla @ Oasix Network
 */
@Singleton
class MenuItemHelper @Inject constructor(
    private val messageHelper: MessageHelper
) {

    companion object {
        private const val BEDROCK_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZjY2ZlNTA5NmEzMzViOWFiNzhhYjRmNzc4YWU0OTlmNGNjYWI0ZTJjOTVmYTM0OTIyN2ZkMDYwNzU5YmFhZiJ9fX0=";
    }

    fun getItem(menu: YAMLFile, vararg key: String) = getItem(menu, emptyMap(), *key)

    fun getItem(menu: YAMLFile, replacers: Map<String, Any>, vararg key: String): ItemProvider {
        val node = menu.get(*key)
        val material = node.node("material").getString("PLAYER_HEAD")

        return when {
            material == "PLAYER_HEAD" -> {
                getSkullBuilder(node, replacers)
            }
            else -> {
                getItemBuilder(node, Material.valueOf(material), replacers)
            }
        }
    }

    private fun getItemBuilder(node: ConfigurationNode, material: Material, replacers: Map<String, Any>): ItemBuilder {
        val itemBuilder = ItemBuilder(material)
            .setDisplayName(messageHelper.deserialize(node.node("display-name").getString(""), replacers).toMenuWrapper())
            .setLore(node.node("lore").getList(String::class.java, emptyList()).map { messageHelper.deserialize(it, replacers).toMenuWrapper() })

        return itemBuilder
    }

    private fun getSkullBuilder(node: ConfigurationNode, replacers: Map<String, Any>): SkullBuilder {
        val skullBuilder = when {
            node.contains("texture") -> SkullBuilder(SkullBuilder.HeadTexture(node.node("texture").getString(BEDROCK_HEAD)))
            replacers.contains("%username%") -> SkullBuilder(replacers["%username%"] as String)
            replacers.contains("%uuid%") -> SkullBuilder(UUID.fromString(replacers["%uuid%"] as String))
            else -> SkullBuilder(SkullBuilder.HeadTexture(node.node("texture").getString(BEDROCK_HEAD)))
        }

        skullBuilder
            .setDisplayName(messageHelper.deserialize(node.node("display-name").getString(""), replacers).toMenuWrapper())
            .setLore(node.node("lore").getList(String::class.java, emptyList()).map { messageHelper.deserialize(it, replacers).toMenuWrapper() })

        return skullBuilder
    }
}

fun Component.toMenuWrapper() = AdventureComponentWrapper(this)