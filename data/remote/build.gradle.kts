plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)

}

android {
    namespace = "com.berlin.remote"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(project(":domain:entity"))
    implementation(project(":data:repository"))
}