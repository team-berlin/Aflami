plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxUi)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.composeUiDebug)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.koin.core)

    implementation(project(":presentation:designSystem"))
    implementation(project(":presentation:safeImageViewer"))
    implementation(project(":presentation:navigation"))
    implementation(project(":presentation:viewModel"))
}