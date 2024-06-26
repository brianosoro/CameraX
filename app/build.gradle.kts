plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.symatechlabs.camerax"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.symatechlabs.camerax"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }


        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        buildFeatures {
            viewBinding = true
        }

        kotlinOptions {
            jvmTarget = "1.8"
        }

    }

}
val cameraX = "1.3.2";
dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.camera:camera-core:$cameraX")
    implementation ("androidx.camera:camera-camera2:$cameraX")
    implementation ("androidx.camera:camera-lifecycle:$cameraX")
    implementation ("androidx.camera:camera-view:$cameraX")
}