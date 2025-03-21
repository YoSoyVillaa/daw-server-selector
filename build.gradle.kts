import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.yosoyvillaa"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.google.inject:guice:5.1.0")
    compileOnly("org.spongepowered:configurate-yaml:4.1.2")
    compileOnly("org.spongepowered:configurate-extra-kotlin:4.1.2")

    implementation("me.fixeddev:commandflow-bukkit:0.5.13-Adventure"){
        exclude("com.google.gson")
        exclude("net.kyori")
    }

    implementation("com.yosoyvillaa.commons:core-guice:1.2.1") {
        exclude("com.google.inject")
        exclude("org.spongepowered")
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<ShadowJar> {
    destinationDirectory.set(project.file("./jars"))
    archiveFileName.set("${rootProject.name}-${project.version}.jar")

    relocate("me.fixeddev", "com.yosoyvillaa.cloudplugin.libs.me.fixeddev")
    relocate("org.intellij.lang.annotations", "com.yosoyvillaa.cloudplugin.libs.org.intellij.lang.annotations")
    relocate("org.jetbrains.annotations", "com.yosoyvillaa.cloudplugin.libs.org.jetbrains.annotations")

    dependencies {
        exclude(dependency("org.jetbrains.kotlin:"))
    }
}