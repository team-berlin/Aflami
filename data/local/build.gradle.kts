plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.local"
    lint {
        disable += "FlowOperatorInvokedInComposition"
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    // Data (local storage)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.androidx.datastore.preferences)

    // Dependency Injection
    implementation(libs.javax.inject)

    // Project Modules
    implementation(project(":data:repository"))
}