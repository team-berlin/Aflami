plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("org.jetbrains.kotlinx.kover")
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
    entity()
    implementation(Dependencies.koin)
    test()
}