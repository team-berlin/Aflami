
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
    lint {
        disable += "FlowOperatorInvokedInComposition"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)


    implementation(libs.javax.inject)
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation ("androidx.work:work-runtime-ktx:2.10.3")
    implementation ("androidx.hilt:hilt-work:1.2.0")


    implementation(project(":data:repository"))
}