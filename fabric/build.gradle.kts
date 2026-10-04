import java.util.jar.JarFile

plugins {
    id("net.fabricmc.fabric-loom")
}

repositories {
    mavenLocal { content { includeGroup("com.hbm") } }
    maven("https://repo.warfactory.co/releases") { content { includeGroup("com.hbm") } }
}

val ntm = "com.hbm:ntm-next-fabric:${property("ntm_version")}"
val ntmBuild = JarFile(configurations.detachedConfiguration(dependencies.create(ntm)).singleFile).use {
    it.manifest.mainAttributes.getValue("Implementation-Version")
}
val ntmVersion = ntmBuild.substringBefore('+')

version = ntmBuild
base.archivesName = "hbm_natives-fabric"
java.toolchain.languageVersion = JavaLanguageVersion.of(25)

sourceSets.main {
    java.setSrcDirs(listOf("../addon/java"))
    resources.setSrcDirs(listOf("../addon/resources", "../addon/fabric"))
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    compileOnly(ntm)
}

tasks.processResources {
    from("../natives") { into("ntm_natives") }
    inputs.property("version", ntmBuild)
    filesMatching("fabric.mod.json") {
        expand("version" to ntmVersion)
        filter { it.replace("\"version\": \"$ntmVersion\"", "\"version\": \"$ntmBuild\"") }
    }
}

tasks.jar {
    from("../LICENSE")
    manifest.attributes("Implementation-Version" to ntmBuild)
}
