plugins {
    id("dev.prism")
}

group = "com.leclowndu93150"
version = "1.0.0"

prism {
    curseMaven()
    maven("Leclowndu93150", "https://maven.leclowndu93150.dev/releases")
    maven("isXander", "https://maven.isxander.dev/releases")

    mod("boids") {
        metadata {
            modId = "boids"
            name = "Boids"
            description = "Replaces the schooling AI of fish and other mobs with a realistic boids flocking simulation. Configurable separation, alignment, and cohesion behaviors."
            license = "MIT"
            author("Leclowndu93150")
            credit("Tomate0613")
        }

        publishing {
            changelogFile = "CHANGELOG.md"
            type = STABLE

            publishCommonJar = true

            curseforge {
                accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
                projectId = "1515836"
            }

            mavenLocal()

            maven {
                name = "Leclowndu93150"
                url = "https://maven.leclowndu93150.dev/releases"
                credentialsFromEnv("MAVEN_USER", "MAVEN_PASS")
            }

            dependencies {
                requires("yacl")
            }
        }

        version("1.20.1") {
            version = "1.0.1"
            common {
                modCompileOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.92.8+1.20.1")
                dependencies {
                    modImplementation("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-fabric")
                    modCompileOnly("curse.maven:modmenu-308702:5162837")
                }
                publishingDependencies {
                    requires("fabric-api")
                    optional("modmenu")
                }
            }
            forge {
                loaderVersion = "47.4.18"
                loaderVersionRange = "[47,)"
                dependencies {
                    modImplementation("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
                }
            }
        }

        version("1.21.1") {
            common {
                compileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.116.11+1.21.1")
                dependencies {
                    modImplementation("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-fabric")
                    modCompileOnly("curse.maven:modmenu-308702:7808443")
                }
                publishingDependencies {
                    requires("fabric-api")
                    optional("modmenu")
                }
            }
            neoforge {
                loaderVersion = "21.1.226"
                loaderVersionRange = "[4,)"
                dependencies {
                    implementation("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
                }
            }
        }

        version("26.1.2") {
            common {
                compileOnly("curse.maven:yacl-667299:7904437")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.146.0+26.1.2")
                dependencies {
                    modImplementation("curse.maven:yacl-667299:7904436")
                    modCompileOnly("curse.maven:modmenu-308702:7813924")
                }
                publishingDependencies {
                    requires("fabric-api")
                    optional("modmenu")
                }
            }
            neoforge {
                loaderVersion = "26.1.2.12-beta"
                loaderVersionRange = "[4,)"
                dependencies {
                    implementation("curse.maven:yacl-667299:7904437")
                }
            }
        }
    }

    mod("birds") {
        metadata {
            modId = "birds"
            name = "Birds"
            description = "Adds flocking birds to the skies of Minecraft. They spawn in forests and use boids flocking behavior to fly in realistic groups."
            license = "MIT"
            author("Leclowndu93150")
            credit("Tomate0613")
        }

        publishing {
            changelogFile = "CHANGELOG.md"
            type = STABLE

            curseforge {
                accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
                projectId = "1515841"
            }

            maven {
                name = "Leclowndu93150"
                url = "https://maven.leclowndu93150.dev/releases"
                credentialsFromEnv("MAVEN_USER", "MAVEN_PASS")
            }

            dependencies {
                requires("boids-reforged")
                requires("yacl")
            }
        }

        version("1.20.1") {
            version = "1.0.1"
            common {
                modCompileOnly("com.leclowndu93150:boids-1.20.1-common:1.0.1")
                modCompileOnly("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.92.8+1.20.1")
                dependencies {
                    modImplementation("com.leclowndu93150:boids-1.20.1-fabric:1.0.1")
                    modImplementation("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-fabric")
                    modCompileOnly("curse.maven:modmenu-308702:5162837")
                }
                publishingDependencies {
                    requires("fabric-api")
                    requires("yacl")
                    optional("modmenu")
                }
            }
            forge {
                loaderVersion = "47.4.18"
                loaderVersionRange = "[47,)"
                dependencies {
                    modImplementation("com.leclowndu93150:boids-1.20.1-forge:1.0.1")
                    modImplementation("dev.isxander:yet-another-config-lib:3.6.6+1.20.1-forge")
                }
            }
        }

        version("1.21.1") {
            common {
                compileOnly("com.leclowndu93150:boids-1.21.1-common:1.0.0")
                compileOnly("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.116.11+1.21.1")
                dependencies {
                    modImplementation("com.leclowndu93150:boids-1.21.1-fabric:1.0.0")
                    modImplementation("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-fabric")
                    modCompileOnly("curse.maven:modmenu-308702:7808443")
                }
                publishingDependencies {
                    requires("fabric-api")
                    requires("yacl")
                    optional("modmenu")
                }
            }
            neoforge {
                loaderVersion = "21.1.226"
                loaderVersionRange = "[4,)"
                dependencies {
                    implementation("com.leclowndu93150:boids-1.21.1-neoforge:1.0.0")
                    implementation("dev.isxander:yet-another-config-lib:3.8.1+1.21.1-neoforge")
                }
            }
        }

        version("26.1.2") {
            common {
                compileOnly("com.leclowndu93150:boids-26.1.2-common:1.0.0")
                compileOnly("curse.maven:yacl-667299:7904437")
            }
            fabric {
                loaderVersion = "0.19.2"
                fabricApi("0.146.0+26.1.2")
                dependencies {
                    modImplementation("com.leclowndu93150:boids-26.1.2-fabric:1.0.0")
                    modImplementation("curse.maven:yacl-667299:7904436")
                    modCompileOnly("curse.maven:modmenu-308702:7813924")
                }
                publishingDependencies {
                    requires("fabric-api")
                    requires("yacl")
                    optional("modmenu")
                }
            }
            neoforge {
                loaderVersion = "26.1.2.12-beta"
                loaderVersionRange = "[4,)"
                dependencies {
                    implementation("com.leclowndu93150:boids-26.1.2-neoforge:1.0.0")
                    implementation("curse.maven:yacl-667299:7904437")
                }
            }
        }
    }
}
