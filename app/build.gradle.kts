plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.dicoding.submiss2"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dicoding.submiss2"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "TOKEN", "\"token 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\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }

        buildFeatures {
            viewBinding = true
            buildConfig = true
        }
    }

    dependencies {
        implementation("androidx.legacy:legacy-support-v4:1.0.0")
        val lifecycle_version = "2.5.0-alpha04"
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")
        implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")
        implementation("io.coil-kt:coil:1.4.0")
        implementation("com.google.code.gson:gson:2.9.0")
        implementation("androidx.activity:activity-ktx:1.4.0")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        implementation("androidx.fragment:fragment-ktx:1.4.1")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
        implementation("androidx.core:core-ktx:1.7.0")
        implementation("androidx.appcompat:appcompat:1.4.1")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.3")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")

        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
        implementation("androidx.room:room-ktx:2.5.2")
        kapt("androidx.room:room-compiler:2.5.2")
        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("androidx.datastore:datastore-preferences-core:1.0.0")
        kapt("androidx.room:room-compiler:2.5.2")
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
