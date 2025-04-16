import com.android.tools.r8.internal.kt
import org.gradle.declarative.dsl.schema.FqName.Empty.packageName

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
    id("com.google.dagger.hilt.android")
    id("com.apollographql.apollo3").version("3.8.5")
    kotlin("kapt")
}

apollo {
   service("service"){
       packageName.set("com.chrisroid.lostintravel")
       introspection {
           endpointUrl.set("https://lostapi.frontendlabs.co.uk/graphql") 
           schemaFile.set(file("src/main/graphql/com/chrisroid/lostintravel/schema.graphqls"))
       }
   }
}

android {
    namespace = "com.chrisroid.lostintravel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.chrisroid.lostintravel"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.navigation.compose)

    // For StateFlow.collectAsState()
    implementation (libs.androidx.lifecycle.runtime.compose)

    // OR if you're using LiveData
    implementation (libs.androidx.runtime.livedata)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation (libs.google.firebase.auth.ktx)
    implementation (libs.play.services.auth)

    // Also add the dependencies for the Credential Manager libraries and specify their versions
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.auth)

    // Hilt Android
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Hilt ViewModel
    implementation(libs.androidx.hilt.navigation.compose) // For Compose integration
    kapt(libs.androidx.hilt.compiler)

    // Lottie for placeholder animations
    implementation(libs.lottie)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Glide for image loading
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation (libs.coil.compose)


    // OkHttp Logging Interceptor for network request debugging
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.datastore.preferences)
    // DataStore
    implementation (libs.androidx.datastore.core)

    // Coroutines (required for DataStore)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    implementation (libs.accompanist.swiperefresh)

    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}