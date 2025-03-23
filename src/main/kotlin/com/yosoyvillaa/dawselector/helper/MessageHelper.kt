package com.yosoyvillaa.dawselector.helper

import com.google.inject.Inject
import com.google.inject.name.Named
import com.yosoyvillaa.dawselector.objects.Components.MINIMESSAGE
import com.yosoyvillaa.commons.core.main.configuration.YAMLFile
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor


class MessageHelper {

    @Inject @Named("messages") private lateinit var messages: YAMLFile

    fun deserialize(replacers: Map<String, Any>, vararg path: String): Component {
        if (replacers.isEmpty())
            return MINIMESSAGE.deserialize(messages.get(*path).getString(path.contentToString()))

        var message = messages.get(*path).getString(path.contentToString())

        for ((key, value) in replacers) message = message.replace(key, value.toString())

        return MINIMESSAGE.deserialize(message)
    }

    fun deserialize(message: String, replacers: Map<String, Any> = emptyMap()): Component {
        if (replacers.isEmpty())
            return MINIMESSAGE.deserialize(message)

        var newMessage = message
        for ((key, value) in replacers) newMessage = newMessage.replace(key, value.toString())

        return MINIMESSAGE.deserialize(newMessage)
    }

    fun colorize(message: String) = ChatColor.translateAlternateColorCodes('&', message)

    fun colorize(message: String, replacers: Map<String, Any>): String {
        if (replacers.isEmpty())
            return colorize(message)

        var newMessage = message

        for ((key, value) in replacers) newMessage = newMessage.replace(key, value.toString())

        return colorize(newMessage)
    }

    fun colorizeList(list: List<String>, replacers: Map<String, Any> = emptyMap()): List<String> {
        val result: MutableList<String> = ArrayList(list.size)

        for (s in list) {
            if (replacers.isEmpty()) {
                result.add(colorize(s))
                continue
            }
            var newS = s
            for ((key, value) in replacers) newS = newS.replace(key, value.toString())

            result.add(colorize(newS))
        }

        return result
    }
}