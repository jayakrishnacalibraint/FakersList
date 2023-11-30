plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Navigation
    id("androidx.navigation.safeargs.kotlin")

    //Dagger-hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    id("kotlin-parcelize")
}

android {
    namespace = "com.example.fakerslist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fakerslist"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Navigation
    val nav_version = "2.7.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    //ViewModel ktx
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    //Livedata Ktx
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    //Lifecycle KTX
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //glide zaa
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //dagger-hilt (replace kapt with annotationProcessor)
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")


    //swipe refresh layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    implementation("com.facebook.shimmer:shimmer:0.5.0")


}
