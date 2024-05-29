Purpose this repo
This is basic android architecture to build new app from scratch based on following
1. kotlin
2. gradle.kts
3. MVVM Framework
4. SOLID Principle
5. Multi Module

Some basic steps to set up some lib/features
Basic Setup

1. Implement Hilt
   1. Put hilt plugin project level build.gradle.kts
      ```id("com.google.dagger.hilt.android") version "2.44" apply false```
   2. in app/module level build.gradle.kts put requirement code as follows
      ```plugins {
        kotlin("kapt")
        id("com.google.dagger.hilt.android")
      }

      android {
       ...
      }

      dependencies {
        implementation("com.google.dagger:hilt-android:2.44")
        kapt("com.google.dagger:hilt-android-compiler:2.44")
      }

      // Allow references to generated code
      kapt {
        correctErrorTypes = true
      }```
   3. Hilt uses Java 8 features. To enable Java 8 in your project, add the following to the app/build.gradle file:
        ```android {
           ...
           compileOptions {
              sourceCompatibility = JavaVersion.VERSION_1_8
              targetCompatibility = JavaVersion.VERSION_1_8
           }
        }```
   4. All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp.
        ```
            @HiltAndroidApp
            class ExampleApplication : Application() { ... }
        ```