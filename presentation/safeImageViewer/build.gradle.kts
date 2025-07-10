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

    //implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.3")
    implementation(libs.tensorflow.lite.metadata)

    implementation(libs.androidx.core.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.androidxUi)
    implementation(libs.koin.core)
    implementation(libs.bundles.test)

    implementation(libs.bundles.coil)

    implementation(project(":presentation:designSystem"))
}