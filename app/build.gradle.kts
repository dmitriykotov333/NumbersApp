import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import java.util.Properties

plugins {
    id("kotdev.android.application")
    id("kotdev.android.application.compose")
    id("kotdev.android.hilt")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.kotdev.numbersapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kotdev.numbersapp"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    //stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    featureFlags = setOf(
        ComposeFeatureFlag.StrongSkipping.disabled(),
        ComposeFeatureFlag.OptimizeNonSkippingGroups
    )
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core_ui"))
    implementation(project(":presentation"))
    implementation(project(":navigation"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
}