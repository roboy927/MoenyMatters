

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.kanishthika.moneymatters"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kanishthika.moneymatters"
        minSdk = 26
        targetSdk = 33
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
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
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets\\database.db")
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.1")
    implementation("androidx.activity:activity-compose:1.9.2")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.foundation:foundation-android:1.7.1")


    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.appcompat:appcompat:1.7.0")



    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("androidx.compose:compose-bom:2024.09.01"))


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.compose.material3:material3-android:1.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.8.5")
    implementation("com.google.firebase:firebase-auth:23.0.0")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //for splashscreen
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //for system ui controller (change status bar color
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    //room database
    val room_version = "2.6.1"

    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC2")

    //Hilt Dependency injection
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    //Navigation Compose Dependencies
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation(kotlin("reflect"))

    implementation ("com.google.code.gson:gson:2.10.1")

    implementation ("com.google.accompanist:accompanist-flowlayout:0.31.0-alpha")

    implementation ("com.google.android.gms:play-services-auth:21.2.0")

    implementation ("androidx.credentials:credentials:1.3.0")


}


