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
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidxUi)
    debugImplementation(libs.bundles.composeUiDebug)
    implementation(libs.bundles.coil)
    implementation(project(":presentation:safeImageViewer"))
}