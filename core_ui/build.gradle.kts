plugins {
    id("kotdev.android.library")
    id("kotdev.android.library.compose")
}

android {
    namespace = "com.kotdev.numbersapp.core_ui"
}

dependencies {
    implementation(libs.compose.shimmer)
    implementation(libs.androidx.core)
    implementation(libs.androidx.collections.immutable)
}