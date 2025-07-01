import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin.android:${Versions.kotlin}" // do we need it here? or is it just a plugin?

    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"

    //ui
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val androidxUi = "androidx.compose.ui:ui"
    const val androidxUiFoundation = "androidx.compose.foundation:foundation"
    const val androidxUiGraphics = "androidx.compose.ui:ui-graphics"
    const val androidxUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"

    const val compose = "org.jetbrains.kotlin.plugin.compose:${Versions.kotlin}" // do we need it here? or is it just a plugin?


    const val ktorAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorCore = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorserialization = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktorlogging = "io.ktor:ktor-client-android:${Versions.ktor}"

    const val koinAndroidxCompose =
        "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidxCompose}"
    const val koinAndroidxComposeNavigation =
        "io.insert-koin:koin-androidx-compose-navigation:${Versions.koinAndroidxCompose}"

    const val navigationCompose = "androidx.navigation:navigation-compose${Versions.navigationCompose}"

    const val coilCompose = "io.coil-kt.coil3:coil-compose:${Versions.coil}"
    const val coilNtework = "io.coil-kt.coil3:coil-network-okhttp:${Versions.coil}"

    const val kover = "org.jetbrains.kotlinx.kover:${Versions.kover}" // do we need it here? or is it just a plugin?

    const val serialization = "org.jetbrains.kotlin.plugin.serialization:${Versions.kotlin}"

    //test
    const val junit = "junit.junit:${Versions.junit}"
    const val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test${Versions.kotlinxCoroutinesTest}"

    const val androidxUiTooling = "androidx.compose.ui:ui-tooling"
    const val androidxUiTestManifest = "androidx.compose.ui:ui-test-manifest"
}

fun DependencyHandler.androidCoreKtx() {
    implementation(Dependencies.androidCoreKtx)
}

fun DependencyHandler.androidxUi() {
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.androidxUi)
    implementation(Dependencies.androidxUiFoundation)
    implementation(Dependencies.androidxUiGraphics)
    implementation(Dependencies.androidxUiToolingPreview)
}

fun DependencyHandler.ktor() {
    implementation(Dependencies.ktorAndroid)
    implementation(Dependencies.ktorCore)
    implementation(Dependencies.ktorserialization)
    implementation(Dependencies.ktorlogging)
}

fun DependencyHandler.koin() {
    implementation(Dependencies.koinAndroidxCompose)
    implementation(Dependencies.koinAndroidxComposeNavigation)
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

fun DependencyHandler.test() {
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.kotlinxCoroutinesTest)
}

fun DependencyHandler.composeUiDebug() {
    debugImplementation(Dependencies.androidxUiTooling)
    debugImplementation(Dependencies.androidxUiTestManifest)
}
