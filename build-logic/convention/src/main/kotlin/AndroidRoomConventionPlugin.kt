import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import extensions.commonExtension
import extensions.configureKotlinAndroid
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            commonExtension.apply {
                defaultConfig {
                    javaCompileOptions {
                        annotationProcessorOptions {
                            arguments += mapOf(
                                "room.schemaLocation" to "${rootProject.projectDir}/schemas",
                                "room.incremental" to "true"
                            )
                        }
                    }
                }
            }
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                add("api", libs.findLibrary("androidx-room-runtime").get())
                add("api", libs.findLibrary("androidx-room-ktx").get())
                add("implementation", libs.findLibrary("androidx-room-paging").get())
                add("ksp", libs.findLibrary("androidx-room-compiler").get())
            }
        }
    }
}