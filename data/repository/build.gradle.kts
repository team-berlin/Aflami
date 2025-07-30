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
                    minValue = 100
                }
            }
        }
    }
}
dependencies {
    implementation(libs.bundles.room)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.datex)
    testImplementation(libs.bundles.test)

    implementation(project(":domain:usecase"))
    implementation(libs.firebase.ml.modeldownloader)
}