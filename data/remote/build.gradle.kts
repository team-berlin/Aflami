plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.remote"
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    // Networking
    implementation(libs.bundles.retrofit)

    // Utilities
    implementation(libs.kotlin.datex)

    // Dependency Injection
    implementation(libs.javax.inject)

    // Project Modules
    implementation(project(":domain:entity"))
    implementation(project(":data:repository"))
}