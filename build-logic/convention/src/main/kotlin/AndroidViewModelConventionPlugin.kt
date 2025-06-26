

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import extensions.commonExtension
import extensions.configureJUnit
import extensions.configureKotlin
import extensions.configureKotlinAndroid
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidViewModelConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            dependencies {
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime").get())
                add("ksp", libs.findLibrary("androidx-lifecycle-compiler").get())
            }
        }
    }
}