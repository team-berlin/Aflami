// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    id("org.jetbrains.kotlinx.kover") version "0.9.0" apply false
}

kover {
    reports {
        filters {
            includes {
                projects=listOf(":domain:usecase,:presentation:viewModel")
            }
        }
        verify {
            rule {
                bound {
                    minValue = 80
                }
            }
        }
    }
}

dependencies {
    kover(project(":domain:usecase"))
    kover(project(":presentation:viewModel"))
}