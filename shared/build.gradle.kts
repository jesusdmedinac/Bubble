import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            api(libs.ktor.client.android)
        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
        jvmMain.dependencies {
            api(libs.ktor.client.cio)
        }
        commonMain.dependencies {
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.client.auth)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.koin.core)

            api(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "com.jesusdmedinac.kmpall.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
