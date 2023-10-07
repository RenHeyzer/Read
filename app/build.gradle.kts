plugins {
    libs.plugins.apply {
        alias(android.application)
        alias(kotlin.android)
        alias(google.services)
    }
}

android {
    namespace = "com.example.read"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.read"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    libs.apply {
        implementation(core.ktx)
        implementation(lifecycle.runtime)
        implementation(activity.compose)
        implementation(platform(compose.bom))
        implementation(bundles.compose)
        implementation(firebase.bom)
    }
}