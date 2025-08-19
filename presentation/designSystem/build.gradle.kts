plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.designsystem"
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    // UI / Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxUi)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)

    // Images
    implementation(libs.bundles.coil)

    // Debug
    debugImplementation(libs.bundles.composeUiDebug)

    // Project Modules
    implementation(project(":presentation:safeImageViewer"))
}