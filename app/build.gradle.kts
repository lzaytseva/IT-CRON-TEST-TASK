plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.github.lzaytseva.it_cron_test_task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.lzaytseva.it_cron_test_task"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val accessToken = property("githubToken")?.toString() ?: error("missing token in gradle.properties")
        buildConfigField("String", "GITHUB_TOKEN", "\"$accessToken\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.rxjava)
    implementation(libs.glide.core)
    implementation(libs.koin)
    implementation(libs.rxJava.core)
    implementation(libs.rxJava.android)
    implementation(libs.kotlinx.serialization)
    implementation(libs.nav.fragment)
    implementation(libs.nav.ui)
    implementation(libs.refreshLayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}