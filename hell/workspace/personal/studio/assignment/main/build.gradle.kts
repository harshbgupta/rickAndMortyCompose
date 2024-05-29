plugins {
    kotlin("kapt")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltLibrary)
    alias(libs.plugins.kotlinParcelize)
}
apply(from = "${rootProject.projectDir}/common.gradle")

android {
    namespace = "co.si.main"
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
        }
        create("qa") {
            dimension = "default"
        }
        create("prod") {
            dimension = "default"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    //auto view binding for gradle 4.0 +
    buildFeatures {
        buildConfig = true
        viewBinding = true
        this.dataBinding = true
    }

}

dependencies {
    //project
    implementation(project(":kernel"))
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}