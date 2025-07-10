plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidxUi)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.composeUiDebug)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.koin.core)

    implementation(project(":presentation:designSystem"))
    implementation(project(":presentation:viewModel"))
}