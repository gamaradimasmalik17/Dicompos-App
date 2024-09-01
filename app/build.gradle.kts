plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.compost"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.compost"
        minSdk = 29
        targetSdk = 34
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation (libs.ui)
    implementation (libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)
    implementation (libs.mpandroidchart)
    implementation(libs.charts)
    implementation(libs.charty)
    implementation ("io.github.ningyuv:circular-seek-bar:0.0.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.5")
    implementation ("io.insert-koin:koin-android:3.2.0")
    implementation ("io.insert-koin:koin-androidx-compose:3.2.0")
    implementation("androidx.compose.runtime:runtime:1.0.5")
    implementation ("androidx.compose.ui:ui-tooling:1.6.8")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")


    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    val vicoVersion = "1.11.1"

    implementation("com.patrykandpatrick.vico:core:$vicoVersion")


    implementation("com.patrykandpatrick.vico:compose:$vicoVersion")


    implementation("com.patrykandpatrick.vico:compose-m2:$vicoVersion")


    implementation("com.patrykandpatrick.vico:compose-m3:$vicoVersion")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


}