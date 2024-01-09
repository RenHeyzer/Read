plugins {
    libs.plugins.apply {
        alias(android.application)
        alias(kotlin.android)
        alias(google.services)
        alias(kotlin.kapt)
        alias(kotlin.serialization)
        alias(kotlin.parcelize)
        alias(hilt.android)
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
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
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
        implementation(activity.ktx)
        implementation(appcompat)
        implementation(material)
        implementation(constraintlayout)
        implementation(lifecycle.runtime)
        implementation(activity.compose)
        implementation(platform(compose.bom))
        implementation(bundles.compose)
        implementation(platform(firebase.bom))
        implementation(bundles.firebase)
        implementation(bundles.paging)
        implementation(bundles.hilt)
        kapt(hilt.android.compiler)
        implementation(bundles.navigation)
        implementation(navigation.compose)
        implementation(coil)
        implementation(glide)
        implementation(view.binding.property.delegate.noreflection)
        implementation(gson)
        implementation(circle.imageview)
        implementation(dots.indicator)
    }
}