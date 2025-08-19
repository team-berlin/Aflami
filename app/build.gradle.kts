import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.gms.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
    alias(libs.plugins.ksp)

    alias(libs.plugins.hilt)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.berlin.aflami"
    compileSdk = 36

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("firebase_release_key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEYSTORE_ALIAS")
            keyPassword = System.getenv("KEYSTORE_PASSWORD")
        }
    }

    lint {
        disable += "FlowOperatorInvokedInComposition"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }


    defaultConfig {
        applicationId = "com.berlin.aflami"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = if (project.hasProperty("versionName")) {
            project.property("versionName") as String
        } else {
            "4.0.0"
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

    bundle {
        language {
            enableSplit = true
        }
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
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.android)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.bundles.test)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.firebase.ml.modeldownloader)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)


    implementation(libs.androidx.worker.runtime.ktx)
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)

    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.coil.compose)

    api(project(":presentation:ui"))
    implementation(project(":presentation:safeImageViewer"))
    implementation(project(":presentation:designSystem"))
    implementation(project(":presentation:viewModel"))
    implementation(project(":domain:usecase"))
    implementation(project(":data:repository"))
    implementation(project(":data:local"))
    implementation(project(":data:remote"))
}