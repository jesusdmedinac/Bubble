import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.codingfeline.buildkonfig")
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val commonDependenciesExceptWasm: org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler.() -> Unit = {
            implementation(libs.voyager.koin)

            implementation(libs.kamel.image)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation("io.insert-koin:koin-core:3.5.6")
            implementation("io.insert-koin:koin-compose:1.1.5")

            implementation("dev.gitlive:firebase-database:1.13.0")
            implementation("dev.gitlive:firebase-auth:1.13.0")

            implementation(libs.orbit.mvi)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.transitions)

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

            implementation(libs.multiplatform.markdown.renderer.m2)

            implementation("com.materialkolor:material-kolor:1.7.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

            commonDependenciesExceptWasm()
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.android)

            implementation(libs.generative.ai)

            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:33.1.1"))
            implementation("com.google.firebase:firebase-crashlytics")
            implementation("com.google.firebase:firebase-analytics")
            implementation("com.google.firebase:firebase-database")

            implementation("com.android.billingclient:billing-ktx:7.0.0")

            commonDependenciesExceptWasm()
        }
        val desktopMain by getting {
            dependencies {
                commonDependenciesExceptWasm()
            }
        }
        val wasmJsMain by getting
    }
}

android {
    namespace = "com.jesusdmedinac.bubble"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jesusdmedinac.bubble"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "apiKey", getPropertiesFile("apiKey"))
        }
        getByName("debug") {
            buildConfigField("String", "apiKey", getPropertiesFile("apiKey"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.jesusdmedinac.bubble"
            packageVersion = "1.0.0"
        }
    }
}

buildkonfig {
    packageName = "com.jesusdmedinac.bubble"

    defaultConfigs {
        buildConfigField(FieldSpec.Type.INT, "versionCode", libs.versions.version.code.get())
        buildConfigField(FieldSpec.Type.STRING, "versionName", libs.versions.version.name.get())
    }
}

fun Project.getPropertiesFile(key: String, file: String = "local.properties"): String {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key)
}

