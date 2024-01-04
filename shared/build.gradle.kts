import org.jetbrains.kotlin.konan.properties.loadProperties

val publishProperties = loadProperties("${rootDir}/publish.properties")

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
    id("maven-publish")
}

group = "com.vardemin.zero.db"
version = "0.0.1"

val GITHUB_USER: String by publishProperties
val GITHUB_TOKEN: String by publishProperties

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Zero DB - simple file based database utilizing Cbor serialization for complex objects"
        homepage = "Link to the Shared Module homepage"
        version = project.version.toString()
        ios.deploymentTarget = "16.0"
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.io)
            implementation(libs.kotlinx.serialization.cbor)
            implementation(libs.concurrent.collections)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation(libs.junit)
        }
    }
}

publishing {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/vardemin/zerodb")
            credentials {
                username = GITHUB_USER
                password = GITHUB_TOKEN
            }
        }
    }
}
