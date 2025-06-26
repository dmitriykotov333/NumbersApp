plugins {
    id("kotdev.android.library")
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "com.kotdev.numbersapp.domain"
}

dependencies {
    api(project(":core"))
    implementation(libs.serialization.json)
}