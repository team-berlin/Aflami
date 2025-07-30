import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.gms.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
    alias(libs.plugins.ksp)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.berlin.aflami"
    compileSdk = 35

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    defaultConfig {
        applicationId = "com.berlin.aflami"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = if (project.hasProperty("versionName")) {
            project.property("versionName") as String
        } else {
            "1.0.0"
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"${properties["BASE_URL"]}\"")
        buildConfigField("String", "API_KEY", "\"${properties["API_KEY"]}\"")
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidxUi)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.bundles.test)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.firebase.ml.modeldownloader)



    api(project(":presentation:ui"))
    implementation(project(":presentation:safeImageViewer"))
    implementation(project(":presentation:designSystem"))
    implementation(project(":presentation:viewModel"))
    implementation(project(":domain:usecase"))
    implementation(project(":data:repository"))
    implementation(project(":presentation:safeImageViewer"))
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
}