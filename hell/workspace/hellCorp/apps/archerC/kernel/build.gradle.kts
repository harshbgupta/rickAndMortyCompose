plugins {
    kotlin("kapt")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltLibrary)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.navSafeArgs)
    alias(libs.plugins.compose)
}
apply(from = "${rootProject.projectDir}/common.gradle")

android {
    namespace = "corp.hell.kernel"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions += "default"
    productFlavors {
        create("dev") {
            dimension = "default"
            buildConfigField("String", "SERVER_NAME", "\"Development\"")
            buildConfigField(
                "String",
                "SERVER_URL",
                "\"https://dev-babble-api.primathontech.co.in\""
            )
            buildConfigField("String", "CHAT_SOCKET_URL", "\"https://dev-chat.github.com\"")
            buildConfigField("String", "VOIP_SOCKET_URL", "\"https://dev-call.github.com\"")
        }
        create("qa") {
            dimension = "default"
            buildConfigField("String", "SERVER_NAME", "\"Staging\"")
            buildConfigField("String", "SERVER_URL", "\"https://qa-api.github.com\"")
            buildConfigField("String", "CHAT_SOCKET_URL", "\"https://qa-chat.github.com\"")
            buildConfigField("String", "VOIP_SOCKET_URL", "\"https://qa-call.github.com\"")
        }
        create("prod") {
            dimension = "default"
            buildConfigField("String", "SERVER_NAME", "\"Production\"")
            buildConfigField("String", "SERVER_URL", "\"https://prod-api.github.com\"")
            buildConfigField("String", "CHAT_SOCKET_URL", "\"https://prod-chat.github.com\"")
            buildConfigField("String", "VOIP_SOCKET_URL", "\"https://prod-call.github.com\"")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    //auto view binding for gradle 4.0 +
    buildFeatures {
        buildConfig = true
        compose = true
    }

}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.graphics.android)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}