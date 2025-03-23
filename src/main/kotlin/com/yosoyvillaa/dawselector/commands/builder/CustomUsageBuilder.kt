package com.yosoyvillaa.dawselector.commands.builder

import com.google.inject.Inject
import com.yosoyvillaa.dawselector.helper.MessageHelper
import me.fixeddev.commandflow.CommandContext
import me.fixeddev.commandflow.usage.UsageBuilder
import net.kyori.adventure.text.Component

class CustomUsageBuilder : UsageBuilder {

    @Inject private lateinit var messageHelper: MessageHelper

    override fun getUsage(ctx: CommandContext): Component {
        val fullCommand = ctx.labels

        return when (fullCommand[0]) {
            else -> messageHelper.deserialize("<red>Something went wrong!")
        }
    }
}