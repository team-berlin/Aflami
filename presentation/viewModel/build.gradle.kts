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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.navigation.compose)
    testImplementation(libs.bundles.test)
    implementation(libs.kotlin.datex)
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation ("app.cash.turbine:turbine:1.1.0")
    val paging_version = "3.3.6"
    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation(libs.androidx.paging.runtime)
    implementation(project(":domain:usecase"))
}
