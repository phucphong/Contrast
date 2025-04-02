plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

}

android {
    namespace = "com.contrast.Contrast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.contrast.Contrast"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xplugin=${rootProject.projectDir}/build.gradle.kts"  // Optional
    }

    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.compiler)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.constraintlayout.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.mlkit.barcode.scanning )

    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.moshi.adapters)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.zoomage)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.places)
    implementation(libs.jsoup)
    implementation(libs.commons.lang3)
    implementation(libs.exoplayer)
    implementation(libs.exoplayerNew)
    implementation(libs.exoplayer.ui)
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.hls)
    implementation(libs.exoplayer.smoothstreaming)
    implementation(libs.isoparser)
    implementation(libs.work.runtime)


    implementation(libs.play.services.places)
    implementation(libs.fancyToast)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
//    implementation(libs.barcodescanner)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.ui)
    implementation(libs.androidx.pager)
    implementation(libs.paging.composer)
    implementation(libs.paging.runtime)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.datepicker)
    implementation(libs.wheelview)


    annotationProcessor(libs.glideCompiler)
    implementation(libs.circleimageview)
    implementation(libs.volley)
    implementation(libs.room.runtime)
//    implementation(libs.room.compiler)
    implementation(libs.okhttp3)
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.firebase.bom)
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.firestore)
//    implementation(libs.firebase.messaging)
//    implementation(libs.firebase.messaging)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
//    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.accompanist.pager)
//    implementation(libs.accompanist.permissions)



//  khai báo thư viện trong project
    implementation(project(":swipe-reveal-layout"))
    implementation(project(":refreshLayout"))
    implementation(project(":singledateandtimepicker"))
    implementation(project(":photoView"))
    implementation(project(":compressor"))
    implementation(project(":domain"))
    implementation(project(":data"))
    // khai báo kps thay cho kapt
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}