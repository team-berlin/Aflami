plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.safeimageviewer"
}

dependencies {

    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.androidx.core.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.androidxUi)
    implementation(libs.androidx.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.koin.core)
    testImplementation(libs.bundles.test)

    implementation(libs.bundles.coil)
}