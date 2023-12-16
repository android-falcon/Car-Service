plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.carservice"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.carservice"
        minSdk = 19
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled=true



        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }


        buildFeatures {
        viewBinding = true
        dataBinding = true
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
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.android.support:support-annotations:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10") // Use the latest version
    //sdp dimen
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Lifecycle + ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    // For navigation
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    //sweet alert dialog
    implementation("com.github.f0ris.sweetalert:library:1.5.6")
    //multidex
    implementation("com.android.support:multidex:1.0.3")
    // gif photo
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

    //RXPermition
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("io.github.jeremyliao:live-event-bus-x:1.8.0")
    //multidex
    implementation ("com.android.support:multidex:1.0.3")


}
