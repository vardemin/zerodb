plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
}

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
        version = "0.0.1"
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
        /*val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.junit)
                // implementation(libs.androix.junit)
                // implementation(libs.robolectric)
            }
        }*/
    }
}

/*
android {
    namespace = "com.vardemin.zero.db"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}*/
