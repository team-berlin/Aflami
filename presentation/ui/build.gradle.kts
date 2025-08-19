plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)

}

android {
    namespace = "com.berlin.ui"
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)

    // UI / Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxUi)
    implementation(libs.bundles.composeUiDebug)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.bundles.coil)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // Project Modules
    implementation(project(":presentation:designSystem"))
    implementation(project(":presentation:safeImageViewer"))
    implementation(project(":presentation:viewModel"))
}
