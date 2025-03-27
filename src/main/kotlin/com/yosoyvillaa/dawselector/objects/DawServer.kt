package com.yosoyvillaa.dawselector.objects

import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class DawServer(
    val name: String,
    val serverId: String,
    val host: String,
)