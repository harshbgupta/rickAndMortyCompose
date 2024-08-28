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
    namespace = "corp.hell.main"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions += "default"
    productFlavors {
        create("dev") {}
        create("qa") {}
        create("prod") {}
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
    implementation(project(":kernel"))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}