// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.gms.service) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.performance) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
subprojects {
    configurations.all {
        resolutionStrategy {
            // Force metadata version
            force("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.7.0")

            // Make sure Compose compiler matches Kotlin version
            force("androidx.compose.compiler:compiler:2.0.21")
        }
    }
}