pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        gradlePluginPortal()
    }
    plugins {
        id("net.fabricmc.fabric-loom") version providers.gradleProperty("loom_version").get()
        id("net.neoforged.moddev") version providers.gradleProperty("moddev_version").get()
    }
}

rootProject.name = "ntm-next-natives-addon"

include("fabric")
include("neoforge")
