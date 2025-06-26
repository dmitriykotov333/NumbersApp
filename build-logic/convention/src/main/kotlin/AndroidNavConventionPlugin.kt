import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class AndroidNavConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("kotdev.android.library")
            }
            dependencies {
                "implementation"(libs.findLibrary("androidx-navigation").get())
                "implementation"(libs.findLibrary("androidx-navigation-ui").get())
                "implementation"(libs.findLibrary("androidx-hilt-navigation").get())

            }
        }
    }

}