import java.util.jar.JarFile

plugins {
    id("net.neoforged.moddev")
}

repositories {
    mavenLocal { content { includeGroup("com.hbm") } }
    maven("https://repo.warfactory.co/releases") { content { includeGroup("com.hbm") } }
}

val ntm = "com.hbm:ntm-next-neoforge:${property("ntm_version")}"
val ntmBuild = JarFile(configurations.detachedConfiguration(dependencies.create(ntm)).singleFile).use {
    it.manifest.mainAttributes.getValue("Implementation-Version")
}

version = ntmBuild
base.archivesName = "hbm_natives-neoforge"
java.toolchain.languageVersion = JavaLanguageVersion.of(25)

sourceSets.main {
    java.setSrcDirs(listOf("../addon/java"))
    resources.setSrcDirs(listOf("../addon/resources", "../addon/neoforge"))
}

neoForge {
    version = property("neo_version") as String
}

dependencies {
    compileOnly(ntm)
}

tasks.processResources {
    from("../natives") { into("ntm_natives") }
    inputs.property("version", ntmBuild)
    filesMatching("META-INF/neoforge.mods.toml") {
        filter { it.replace("versionRange = \"[0,)\"", "versionRange = \"[$ntmBuild]\"") }
    }
}

tasks.jar {
    from("../LICENSE")
    manifest.attributes("Implementation-Version" to ntmBuild)
}
