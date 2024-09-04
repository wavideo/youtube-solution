plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.youtubesolution"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.youtubesolution"
        minSdk = 31
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("androidx.fragment:fragment-ktx:1.6.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-analytics") // 파이어베이스 앱 분석
    implementation("com.google.firebase:firebase-auth-ktx") // 파이어베이스 인증
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0") // 파이어베이스 인증

    implementation("com.google.firebase:firebase-firestore-ktx")

//    implementation ("org.komoran:komoran-core:3.0.1") // 형태소 분석기


}