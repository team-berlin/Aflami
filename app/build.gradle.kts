plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.gms.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
}

android {
    namespace = "com.berlin.aflami"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.berlin.aflami"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidxUi)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.koin.core)
    implementation(libs.bundles.test)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    api(project(":presentation:ui"))
    implementation(project(":presentation:designSystem"))
}