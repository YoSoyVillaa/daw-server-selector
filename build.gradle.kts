import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

group = "com.yosoyvillaa"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.google.inject:guice:5.1.0")
    compileOnly("org.spongepowered:configurate-yaml:4.1.2")
    compileOnly("org.spongepowered:configurate-extra-kotlin:4.1.2")

    implementation("xyz.xenondevs.invui:invui:1.44")
    implementation("xyz.xenondevs.invui:invui-kotlin:1.44")

    implementation("me.fixeddev:commandflow-bukkit:0.5.14-Adventure"){
        exclude("com.google.gson")
        exclude("net.kyori")
    }

    implementation("com.yosoyvillaa.commons:core-guice:1.2.2") {
        exclude("com.google.inject")
        exclude("org.spongepowered")
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<ShadowJar> {
    destinationDirectory.set(project.file("./jars"))
    archiveFileName.set("${rootProject.name}-${project.version}.jar")

    relocate("me.fixeddev", "com.yosoyvillaa.dawselector.libs.me.fixeddev")
    relocate("org.intellij.lang.annotations", "com.yosoyvillaa.dawselector.libs.org.intellij.lang.annotations")
    relocate("org.jetbrains.annotations", "com.yosoyvillaa.dawselector.libs.org.jetbrains.annotations")
    relocate("xyz.xenondevs.invui", "com.yosoyvillaa.dawselector.libs.xyz.xenondevs.invui")

    dependencies {
        exclude(dependency("org.jetbrains.kotlin:"))
    }
}