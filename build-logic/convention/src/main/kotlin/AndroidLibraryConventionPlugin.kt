import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import com.android.build.gradle.LibraryExtension
import extensions.configureJUnit
import extensions.configureKotlin
import extensions.configureKotlinAndroid
import extensions.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlin()
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }

            configurations.configureEach {
                resolutionStrategy {
                    force(libs.findLibrary("junit").get())
                }
            }
            configureJUnit()
        }

    }
}