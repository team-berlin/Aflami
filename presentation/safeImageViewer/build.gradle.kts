plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aflami.custom.plugin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.berlin.safeimageviewer"
    buildFeatures {
        mlModelBinding = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    // UI / Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxUi)
    implementation(libs.bundles.composeUiDebug)
    implementation(libs.bundles.coil)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // DataStore
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core.android)

    // Firebase
    implementation(libs.firebase.ml.modeldownloader)

    // TensorFlow Lite
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.gpu)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Project Modules
    implementation(project(":data:local"))
}