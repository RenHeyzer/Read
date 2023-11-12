plugins {
    libs.plugins.apply {
        alias(android.application)
        alias(kotlin.android)
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
            buildConfigField(
                "String",
                "supabaseUrl",
                "\"https://ckipllpkakxblzzpkjnj.supabase.co\""
            )
            buildConfigField(
                "String",
                "supabaseKey",
                "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNraXBsbHBrYWt4Ymx6enBram5qIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTkxOTM1MDQsImV4cCI6MjAxNDc2OTUwNH0.geF38px8D4TddS5Tlp3NYpGxqwlKoucRRlEUY3MZqfE\""
            )
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
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
        implementation(coil.compose)
        implementation(bundles.paging)
        implementation(bundles.hilt)
        kapt(hilt.android.compiler)
        implementation(navigation.compose)
        implementation(accompanist.systemuicontroller)
        implementation(gson)
        implementation(platform(supabase.bom))
        implementation(bundles.supabse)
        implementation(ktor.client.android)
    }
}