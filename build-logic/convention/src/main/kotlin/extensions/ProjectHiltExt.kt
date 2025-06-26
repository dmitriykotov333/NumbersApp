package extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidHilt(
    commonExtension: CommonExtension<
            *,
            *,
            *,
            *,
            *,
            *,
            >
) {
    if (commonExtension is ApplicationExtension) {
        pluginManager.apply(libs.findPlugin("dagger-hilt").get().get().pluginId)// apply("dagger.hilt.android.plugin")

    }
    pluginManager.apply("com.google.devtools.ksp")
    dependencies {
        add("implementation", libs.findLibrary("androidx-hilt").get())
        add("ksp", libs.findLibrary("androidx-hilt-compiler").get())
        add("implementation", libs.findLibrary("autodagger-android").get())
        add("ksp", libs.findLibrary("autodagger-compiler").get())
    }
}