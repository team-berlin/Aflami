plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
}

android {
    namespace = "com.berlin.viewModel"
}

kover {

    reports {
        filters {
            includes {
                classes("**ViewModel")
                classes("**viewModel")
                classes("**viewmodel")
                classes("**Viewmodel")
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
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navigation
    implementation(libs.navigation.compose)

    // Utilities
    implementation(libs.kotlin.datex)


    // Paging
    implementation(libs.androidx.paging.runtime)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Project Modules
    implementation(project(":domain:usecase"))

    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(libs.turbine)
}
