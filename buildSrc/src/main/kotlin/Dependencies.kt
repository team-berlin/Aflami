import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"

    //ui
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val androidxUi = "androidx.compose.ui:ui"
    const val androidxUiFoundation = "androidx.compose.foundation:foundation"
    const val androidxUiGraphics = "androidx.compose.ui:ui-graphics"
    const val androidxUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"

    const val ktorCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorserialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorlogging = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    const val ktorContentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"

    const val koin = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    const val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"

    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"

    const val coilCompose = "io.coil-kt.coil3:coil-compose:${Versions.coil}"
    const val coilNtework = "io.coil-kt.coil3:coil-network-okhttp:${Versions.coil}"

    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    const val androidxMaterial3 = "androidx.compose.material3:material3"

    //firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAnalyticsKtx = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlyticsktx = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebasePerfKtx = "com.google.firebase:firebase-perf-ktx"

    //test
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutinesTest}"

    const val androidxUiTooling = "androidx.compose.ui:ui-tooling"
    const val androidxUiTestManifest = "androidx.compose.ui:ui-test-manifest"
}

fun DependencyHandler.androidCoreKtx() {
    implementation(Dependencies.androidCoreKtx)
}

fun DependencyHandler.lifecycleRuntimeKtx() {
    implementation(Dependencies.lifecycleRuntimeKtx)
}

fun DependencyHandler.androidxUi() {
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.androidxUi)
    implementation(Dependencies.androidxUiFoundation)
    implementation(Dependencies.androidxUiGraphics)
    implementation(Dependencies.androidxUiToolingPreview)
    implementation(Dependencies.androidxMaterial3)
}

fun DependencyHandler.ktor() {
    implementation(Dependencies.ktorAndroid)
    implementation(Dependencies.ktorCore)
    implementation(Dependencies.ktorserialization)
    implementation(Dependencies.ktorlogging)
    implementation(Dependencies.ktorCio)
    implementation(Dependencies.ktorContentNegotiation)
}

fun DependencyHandler.koin() {
    implementation(Dependencies.koin)
    implementation(Dependencies.koinAndroid)
    implementation(Dependencies.koinCompose)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.navigationCompose)
}

fun DependencyHandler.coil() {
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.coilNtework)
}

fun DependencyHandler.serialization() {
    implementation(Dependencies.serialization)
}

fun DependencyHandler.firebase() {
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalyticsKtx)
    implementation(Dependencies.firebasePerfKtx)
    implementation(Dependencies.firebaseCrashlyticsktx)
}

fun DependencyHandler.test() {
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.kotlinxCoroutinesTest)
}

fun DependencyHandler.composeUiDebug() {
    debugImplementation(Dependencies.androidxUiTooling)
    debugImplementation(Dependencies.androidxUiTestManifest)
}

fun DependencyHandler.designSystem() {
    implementation(project(":presentation:designSystem"))
}

fun DependencyHandler.viewModel() {
    implementation(project(":presentation:viewModel"))
}

fun DependencyHandler.useCase() {
    implementation(project(":domain:usecase"))
}

fun DependencyHandler.entity() {
    api(project(":domain:entity"))
}

fun DependencyHandler.repository() {
    implementation(project(":data:repository"))
}

fun DependencyHandler.ui() {
    api(project(":presentation:ui"))
}

fun DependencyHandler.remote() {
    implementation(project(":data:remote"))
}