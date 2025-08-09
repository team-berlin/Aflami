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
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.composeUiDebug)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.androidxUi)
    implementation(libs.bundles.coil)
    testImplementation(libs.bundles.test)
    implementation(libs.firebase.ml.modeldownloader)
    implementation (libs.tensorflow.lite.gpu)

    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.android)
    implementation(libs.androidx.datastore.preferences)

    ksp(libs.hilt.android.compiler)

    implementation(libs.hilt.android)

    implementation(project(":data:local"))

}