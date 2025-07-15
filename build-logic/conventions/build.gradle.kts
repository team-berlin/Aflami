plugins {
    `kotlin-dsl`
}

group = "com.berlin.conventions"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("AndroidLibraryCommonPlugin") {
            id = "com.berlin.plugin"
            implementationClass = "AndroidLibraryCommonPlugin"
        }
    }
}