import java.util.Properties

plugins {
    id("kotdev.core.ui")
    id("kotdev.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kotdev.numbersapp.core"
    defaultConfig {
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val baseUrl = localProperties["BASE_URL"] as String
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    }
}


dependencies {
    api(libs.androidx.coroutine)
    api(libs.androidx.coroutine.core)
    api(libs.androidx.collections.immutable)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.viewmodel.savedstate)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.negotitation)
    implementation(libs.ktor.koltin.json)
}