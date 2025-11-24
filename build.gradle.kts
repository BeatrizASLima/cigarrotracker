// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val kspVersion = "2.2.21-2.0.4" // 1st part of KSP version must match kotlin version used in the build
    id("com.google.devtools.ksp") version "$kspVersion" apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    val room_version = "2.8.3"
    id("androidx.room") version "$room_version" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}