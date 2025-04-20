plugins {
    kotlin("android")
    id("com.android.library")
}

android {
    namespace = "com.party.shared"
    compileSdk = 35

    defaultConfig {
        minSdk = 30
        targetSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.core)
}
