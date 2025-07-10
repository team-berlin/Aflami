plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.local"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":data:repository"))
}