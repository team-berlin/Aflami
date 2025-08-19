plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aflami.custom.plugin)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.berlin.repository"
}
kover {
    reports {
        filters {
            includes {
                classes("**impl")
                classes("**Impl")

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
    // Data
    implementation(libs.bundles.room)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.datex)

    // Dependency Injection
    implementation(libs.javax.inject)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Project Modules
    implementation(project(":domain:usecase"))

    // Testing
    testImplementation(libs.bundles.test)
}