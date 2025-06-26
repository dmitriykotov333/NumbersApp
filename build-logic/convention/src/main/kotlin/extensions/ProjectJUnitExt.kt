package extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

fun Project.configureJUnit() {
    dependencies {
        add("androidTestImplementation", libs.findLibrary("junit").get())
        add("testImplementation", libs.findLibrary("junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-espresso-core").get())
        add("androidTestImplementation", libs.findLibrary("androidx-compose-bom").get())
        //add("androidTestImplementation", libs.findLibrary("androidx-ui-test-junit4").get())
        //add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
        //add("debugImplementation", libs.findLibrary("androidx-ui-test-manifest").get())
    }
}