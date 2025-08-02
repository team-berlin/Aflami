plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
    //alias(libs.plugins.dagger.hilt)
    //kotlin("kapt")
}

android {
    namespace = "com.berlin.local"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)

    implementation(libs.javax.inject)

    implementation(project(":data:repository"))
}