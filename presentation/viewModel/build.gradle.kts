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
    implementation(libs.bundles.koin)
    testImplementation(libs.bundles.test)
    implementation(libs.kotlin.datex)
    implementation("io.arrow-kt:arrow-optics:2.1.2")
    implementation("io.arrow-kt:arrow-core:2.1.2")
    ksp("io.arrow-kt:arrow-optics-ksp-plugin:2.1.2")

    val paging_version = "3.3.6"
    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation(project(":domain:usecase"))
}
