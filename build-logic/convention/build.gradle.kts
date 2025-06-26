import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.kotdev.numbersapp.buildlogic"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "kotdev.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "kotdev.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidViewModel") {
            id = "kotdev.android.viewmodel"
            implementationClass = "AndroidViewModelConventionPlugin"
        }

        register("androidApplication") {
            id = "kotdev.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "kotdev.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "kotdev.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidRoom") {
            id = "kotdev.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidTest") {
            id = "kotdev.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        register("coreUi") {
            id = "kotdev.core.ui"
            implementationClass = "CoreUiConventionPlugin"
        }

        register("jvmLibrary") {
            id = "kotdev.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("navigation") {
            id = "kotdev.navigation"
            implementationClass = "AndroidNavConventionPlugin"
        }
    }
}