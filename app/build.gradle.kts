plugins {
    id("com.android.application")
}

android {
    namespace = "com.hydrobuddy.mobile"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hydrobuddy.mobile"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
}
