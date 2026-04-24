plugins {
    id("com.gradleup.shadow") version "9.3.2"
    id("xyz.jpenilla.run-paper") version "2.3.0"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:6.1.0-beta.1")
    implementation("com.google.inject:guice:7.0.0")
    implementation("org.reflections:reflections:0.10.2")

    implementation("dev.rollczi:litecommands-bukkit:3.10.9")

    implementation("com.eternalcode:multification-paper:1.2.4")
    implementation("com.eternalcode:multification-okaeri:1.2.4")

    implementation("dev.triumphteam:triumph-gui:3.1.13")

    implementation(project(":common"))
}

tasks {
    shadowJar {
        archiveFileName.set("NxtrIdCards-${project.version}.jar")
        destinationDirectory.set(file("$buildDir/libs"))
    }

    runServer {
        minecraftVersion("1.21.11")
        dependsOn(shadowJar)

        doFirst {
            val kotlinStdlib = configurations.runtimeClasspath.get()
                .filter { it.name.startsWith("kotlin-stdlib") }
                .joinToString(";") { it.absolutePath }
            jvmArgs("-Xbootclasspath/a:$kotlinStdlib")

            val debugAgent = file("${rootProject.buildDir}/libs/kotlinx-coroutines-debug.jar")
            if (debugAgent.exists()) {
                jvmArgs("-javaagent:${debugAgent.absolutePath}")
            }
        }
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}