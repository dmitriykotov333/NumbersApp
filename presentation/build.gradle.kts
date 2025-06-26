plugins {
    id("kotdev.android.library")
    id("kotdev.android.hilt")
    id("kotdev.core.ui")
    id("kotdev.android.viewmodel")
}

android {
    namespace = "com.kotdev.numbersapp.presentation"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":core_ui"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.serialization.json)
    implementation(libs.androidx.coil.compose)
    implementation(libs.androidx.collections.immutable)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.lottie)
}