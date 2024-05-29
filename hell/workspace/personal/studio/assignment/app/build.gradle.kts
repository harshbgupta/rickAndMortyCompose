plugins {
    kotlin("kapt")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltLibrary)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.googleServices)
}
val appVersionCode = libs.versions.versionMajor.get().toInt() + libs.versions.versionMinor.get()
    .toInt() + libs.versions.versionBuild.get().toInt() + libs.versions.versionPatch.get().toInt()
val appVersionName = "${libs.versions.versionMajor.get()}.${libs.versions.versionMinor.get()}" +
        ".${libs.versions.versionBuild.get()}.${libs.versions.versionPatch.get()}"

apply(from = "${rootProject.projectDir}/common.gradle")
android {
    namespace = "corp.hell.archer"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "corp.hell.archer"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "default"
    productFlavors {
        create("dev") { dimension = "default" }
        create("qa") { dimension = "default" }
        create("prod") { dimension = "default" }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../imp/dev_archer_1800007007.jks")
            storePassword = "1800007007"
            keyPassword = "1800007007"
            keyAlias = "archer"
        }
        create("release") {
            storeFile = file("../imp/release_archer_1800007007.jks")
            storePassword = "1800007007"
            keyPassword = "1800007007"
            keyAlias = "archer"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    //auto view binding for gradle 4.0 +
    buildFeatures {
        buildConfig = true
        viewBinding = true
        this.dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .filterIsInstance<com.android.build.gradle.internal.api.BaseVariantOutputImpl>()
            .forEach {
                it.outputFileName =
                    "${variant.name}-v${appVersionName}.apk"
            }
    }
}

dependencies {
    //project
    implementation(project(":kernel"))
    implementation(project(":main"))

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}