plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version "1.3.14"
    id("xyz.wagyourtail.jvmdowngrader") version "1.2.2"
}

group = "dev.neuralnexus"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    maven("https://maven.neuralnexus.dev/mirror")
}

val minecraftVersion = project.properties["minecraft_version"] as String


val forgeDowngraded by sourceSets.creating {
    runtimeClasspath += sourceSets.main.get().runtimeClasspath
}

repositories {
    maven("https://maven.neuralnexus.dev/mirror")

    flatDir {
        dirs("${rootProject.projectDir}/libs")
    }
}

unimined.minecraft(sourceSets.main.get(), forgeDowngraded) {
    version(project.properties["minecraft_version"] as String)

    mappings {
        searge()
        mcp("stable", "12-1.7.10")
    }

    minecraftForge {
        loader(project.properties["forge_version"] as String)
        mixinConfig("morehealthserverfix.mixins.json")
    }

    defaultRemapJar = false

    if (sourceSet == sourceSets.main.get()) {
        runs.off = true
    }

    if (sourceSet == forgeDowngraded) {
        remap(jvmdg.defaultShadeTask.get(), "remapJar") {
            asJar.archiveClassifier.set("final")
            mixinRemap {
                disableRefmap()
            }
        }
    }
}

dependencies {
    "modImplementation"("com.github.LegacyModdingMC.UniMixins:unimixins-all-1.7.10:"+project.properties["unimixins_version"]+":dev")
    "compileOnly"(":More Health Forge")

    "forgeDowngradedImplementation"("xyz.wagyourtail.jvmdowngrader:jvmdowngrader-java-api:${jvmdg.version}:downgraded-8")
    "forgeDowngradedImplementation"(jvmdg.defaultTask.get().outputs.files)
}

tasks.compileJava {
    options.release.set(21)
}

tasks.build {
    dependsOn("remapJar")
}
