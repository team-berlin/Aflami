plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.aflami.custom.plugin)
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.berlin.viewModel"
}
kover {
    reports {
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
    implementation(libs.koin.core)
    implementation(libs.bundles.test)

    implementation(project(":domain:usecase"))
}