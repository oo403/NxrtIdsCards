plugins {
    kotlin("jvm") version "2.3.20-RC3"
    id("com.gradleup.shadow") version "9.3.2"
}

allprojects {
    group = "pl.sirox"
    version = "1.0.0"
}

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc-repo"
        }

        maven("https://repo.okaeri.cloud/releases") {
            name = "okaeri-repo"
        }

        maven("https://repo.panda-lang.org/releases") {
            name = "panda-repo"
        }
    }

    apply {
        plugin("kotlin")
        plugin("com.gradleup.shadow")
    }

    kotlin {
        jvmToolchain(21)
    }
}

tasks.build {
    dependsOn("shadowJar")
}