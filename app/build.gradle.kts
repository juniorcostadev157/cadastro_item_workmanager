import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    jacoco
    id("com.google.gms.google-services")
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.hilt.android)

}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("local.properties")

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    signingConfigs {
        create("release") {
            val storeFilePath = findProperty("android.injected.signing.store.file") as String?
                ?: keystoreProperties["RELEASE_STORE_FILE"] as? String
            val storePassword = findProperty("android.injected.signing.store.password") as String?
                ?: keystoreProperties["RELEASE_STORE_PASSWORD"] as? String
            val keyAlias = findProperty("android.injected.signing.key.alias") as String?
                ?: keystoreProperties["RELEASE_KEY_ALIAS"] as? String
            val keyPassword = findProperty("android.injected.signing.key.password") as String?
                ?: keystoreProperties["RELEASE_KEY_PASSWORD"] as? String

            if (storeFilePath != null && storePassword != null && keyAlias != null && keyPassword != null) {
                storeFile = file(storeFilePath)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
            } else {
                println("⚠️ Configurações de assinatura não encontradas. Release build não será assinada.")
            }
        }
    }

    namespace = "com.junior.projetomvvmcleanxml"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.junior.projetomvvmcleanxml"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        signingConfig = signingConfigs.getByName("release")
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    viewBinding {
        enable = true
    }

    tasks.register<JacocoReport>("jacocoTestReport") {
        dependsOn("testDebugUnitTest")

        reports {
            xml.required.set(true)
            html.required.set(true)
            csv.required.set(false)
        }

        val fileFilter = listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*"
        )

        val debugTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
            exclude(fileFilter)
        }

        classDirectories.setFrom(debugTree)
        sourceDirectories.setFrom(files("src/main/java"))
        executionData.setFrom(fileTree(layout.buildDirectory) {
            include("jacoco/testDebugUnitTest.exec")
        })
    }
    buildFeatures {
        viewBinding = true
    }


}
room {
    // cria a pasta schemas na raiz do módulo :app
    schemaDirectory("$projectDir/schemas")
}
dependencies {

    //--- CORE E BASE ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.coordinatorlayout)

    //  --- NAVEGAÇÃO ---
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //  --- UI COMPONENTES ---
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)

    //  --- ROOM (PERSISTÊNCIA LOCAL) ---
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //  --- LIFECYCLE (ViewModel, LiveData, etc) ---
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    implementation(libs.androidx.lifecycle.livedataKtx)

    //  --- WORKMANAGER ---
    implementation(libs.androidx.work.runtime.ktx)

    //  --- FIREBASE (BOM gerencia as versões) ---
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.crashlytics)

    //  --- HILT (Injeção de Dependência) ---
    implementation(libs.androidx.hilt)
    ksp(libs.androidx.hilt.compiler)

    //  --- HILT + WORKMANAGER ---
    implementation(libs.androidx.work.hilt)
    ksp(libs.androidx.hilt.compiler.work)

    //--- TESTES UNITÁRIOS ---
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(kotlin("test"))

    // --- TESTES INSTRUMENTADOS (ESPRESSO / ANDROIDX TEST) ---
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.hamcrest)
    androidTestImplementation(libs.androidx.fragment.testing)
    debugImplementation(libs.androidx.fragment.testing.manifest)


    // --- HILT PARA TESTES INSTRUMENTADOS ---
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler.testing)


}
