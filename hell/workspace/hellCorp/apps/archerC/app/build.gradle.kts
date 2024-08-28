plugins {
    kotlin("kapt")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltLibrary)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.navSafeArgs)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.compose)
}

apply(from = "${rootProject.projectDir}/common.gradle")

val appVersionCode = libs.versions.versionMajor.get().toInt() + libs.versions.versionMinor.get()
    .toInt() + libs.versions.versionBuild.get().toInt() + libs.versions.versionPatch.get().toInt()
val appVersionName = "${libs.versions.versionMajor.get()}.${libs.versions.versionMinor.get()}" +
        ".${libs.versions.versionBuild.get()}.${libs.versions.versionPatch.get()}"

//for keystore
val aliasKeyStore = "archer"
val passwordKeyStore = "1800007007"
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
        create("dev") {
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Compressor Dev")
            dimension = "default"
        }
        create("qa") {
            applicationIdSuffix = ".qa"
            resValue("string", "app_name", "Compressor QA")
            dimension = "default"
        }
        create("prod") {
            dimension = "default"
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../imp/debug_${aliasKeyStore}_${passwordKeyStore}.jks")
            storePassword = passwordKeyStore
            keyPassword = passwordKeyStore
            keyAlias = aliasKeyStore
        }
        create("release") {
            storeFile = file("../imp/release_${aliasKeyStore}_${passwordKeyStore}.jks")
            storePassword = passwordKeyStore
            keyPassword = passwordKeyStore
            keyAlias = aliasKeyStore
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

kotlin {
    jvmToolchain(17)
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