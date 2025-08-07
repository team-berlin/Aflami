plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kover)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
kover {
    reports {
        filters {
            includes {
                classes("**UseCase")
                classes("**useCase")
                classes("**usecase")
                classes("**Usecase")
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
    testImplementation(libs.bundles.test)
    implementation(libs.kotlin.datex)
    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.core) // or latest stable
    implementation(libs.kotlinx.coroutines.android)

    api(project(":domain:entity"))
}