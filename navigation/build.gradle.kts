import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    id("kotdev.android.library")
    id("kotdev.navigation")
    alias(libs.plugins.compose.compiler)
    id("kotdev.core.ui")
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}
android {
    namespace = "com.kotdev.numbersapp.navigation"
}

composeCompiler {
  reportsDestination = layout.buildDirectory.dir("compose_compiler")
    featureFlags = setOf(
        ComposeFeatureFlag.StrongSkipping.disabled(),
        ComposeFeatureFlag.OptimizeNonSkippingGroups
    )
}

dependencies {
    implementation(libs.serialization.json)
}