plugins {
    id("kotdev.android.library")
    id("kotdev.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kotdev.numbersapp.data"
}

dependencies {
    api(project(":domain"))
    api(project(":database"))
    api(project(":navigation"))
    implementation(libs.ktor.core)
    implementation(libs.ktor.json)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.negotitation)
    implementation(libs.ktor.koltin.json)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.cio)
    implementation(libs.serialization.json)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.room.paging)
}