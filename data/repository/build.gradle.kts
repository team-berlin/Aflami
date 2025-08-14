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
    implementation(libs.bundles.room)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.datex)
    testImplementation(libs.bundles.test)
    implementation(libs.javax.inject)

    implementation ("androidx.work:work-runtime-ktx:2.10.3")
    implementation ("androidx.hilt:hilt-work:1.2.0")
    ksp(libs.hilt.android.compiler)

    implementation(libs.hilt.android)
    implementation(project(":domain:usecase"))
    implementation(libs.firebase.ml.modeldownloader)
}