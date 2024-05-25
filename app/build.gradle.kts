import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("kapt")
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ktLint)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.dhruvv.expensetracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhruvv.expensetracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 4
        versionName = "1.0"

        testInstrumentationRunner = "com.dhruvv.expensetracker.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }

        // Loop through all configured build variants in the project
        applicationVariants.all {
            // Assign the current build variant to a variable for easier access
            val variant = this
            // Loop through all output files associated with the current build variant
            variant.outputs
                .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    // Extract information about the current build variant
                    // Name of the product flavor (empty string if none)
                    val flavour = variant.flavorName
                    // Name of the build type (e.g., 'debug' or 'release')
                    val builtType = variant.buildType.name
                    // Version name defined for the project
                    val versionName = variant.versionName
                    // Version code defined for the project
                    val vCode = variant.versionCode

                    // Construct a custom filename based on variant properties
                    output.outputFileName =
                        "ExpenseTracker-${flavour}-${builtType}-${versionName}(${vCode}).apk".replace(
                            "-${flavour}",
                            ""
                        )

                    // This line sets the 'outputFileName' property of the 'output' object
                    // The 'output' object likely represents an individual APK file generated
                    // for the current build variant. The value assigned is a string combining
                    // several elements to create an informative filename.
                }
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
    kapt {
        correctErrorTypes = true
    }

    afterEvaluate {
        val copyReleaseApk by tasks.creating(Copy::class) {
            from("$rootDir/release")
            into("$$rootDir/fastlane" + "/outputs/apk/release")
            // You can also rename the file here using 'rename'
        }
    }

    ktlint {
        android = true
        ignoreFailures = false
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.SARIF)
            reporter(ReporterType.CHECKSTYLE)
        }
        outputToConsole.set(true)
        outputColorName.set("YELLOW")
    }
}

tasks.getByPath("preBuild").dependsOn("ktlintCheck")

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.core.testing)
    testImplementation("junit:junit:4.12")
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    // dagger hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing) // For instrumented tests.
    kaptAndroidTest(libs.hilt.android.compiler)

    // room
    implementation(libs.room.android)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)
    // optional - Test helpers
    testImplementation(libs.room.testing)

    // coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)

    // test flow
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)

    // number keypad
    implementation(libs.number.keyboard)

    implementation(libs.androidx.material.icons.extended.android)

    // navigation
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // lottie animations
    implementation(libs.lottie.compose)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}
