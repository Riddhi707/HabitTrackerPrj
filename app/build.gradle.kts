plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.appdistribution)
}

android {
    namespace = "com.example.habit_tracker_prj"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habit_tracker_prj"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    // Firebase BOM in Kotlin DSL
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Material Components library
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.google.android.material:material:1.6.1")



    // implementation(libs.firebase.firestore)
    // give reference to libs.versions.toml

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
   // implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}