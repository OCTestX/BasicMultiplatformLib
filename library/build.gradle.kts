import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    kotlin("plugin.serialization") version "1.9.24"
    id("app.cash.sqldelight") version "2.0.2"
//    id("com.moriatsushi.cacheable") version "0.0.3"
}

group = "io.github.octestx"
version = "0.0.1"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_21)
//        }
    }
//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()
//    linuxX64()
//    @OptIn(ExperimentalWasmDsl::class, ExperimentalWasmDsl::class)
//    wasmJs {
//        browser {
//            val rootDirPath = project.rootDir.path
//            val projectDirPath = project.projectDir.path
//            commonWebpackConfig {
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(rootDirPath)
//                        add(projectDirPath)
//                    }
//                }
//            }
//        }
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.kotlinx.serialization.json)
                //
                implementation (libs.log4j.api)
                implementation (libs.log4j.core)
                implementation(libs.log4j.slf4j.impl)
                implementation(libs.klogging.jvm)
                //https://github.com/russhwolf/multiplatform-settings?tab=readme-ov-file#make-observable-module
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.no.arg)
                //https://github.com/xxfast/KStore
                implementation(libs.kstore)
                implementation(libs.kstore.file)
                //https://github.com/DevSrSouza/compose-icons
                //https://tabler.io/icons
                implementation(libs.tabler.icons)
                //https://github.com/syer10/Kotlin-Multiplatform-AppDirs
                implementation(libs.kotlin.multiplatform.appdirs)
                //https://sqldelight.github.io/sqldelight/2.0.2/

                //https://github.com/ReactiveCircus/cache4k
                implementation(libs.cache4k)
                //https://github.com/mori-atsushi/kotlin-cacheable
//                implementation(libs.cacheable.core)
                //https://github.com/InsertKoinIO/koin
                implementation(libs.koin.core)
                //https://github.com/cashapp/molecule/tree/trunk
//                implementation(libs.molecule.runtime)
                //https://github.com/Tlaster/PreCompose
//                api(libs.precompose)
//                api(libs.precompose.koin)
//                api(libs.precompose.molecule)
                //
                implementation(libs.okio) // 请检查是否有更新版本
            }
        }
        jvmMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            implementation(libs.sqlite.driver)
        }
        androidMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            implementation(libs.android.driver)
            //https://github.com/InsertKoinIO/koin
            implementation(libs.koin.android)
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "io.github.octestx"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("io.github.octestx")
        }
    }
}


mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "basic-multiplatform-lib", version.toString())

    pom {
        name = "BasicMultiplatformLib"
        description = "提供很多基本特性包括日志，设置存储，json序列化"
        inceptionYear = "2025"
        url = "https://github.com/kotlin/multiplatform-library-template/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "OCTestX"
                name = "OCTestX"
                url = "https://github.com/OCTestX"
            }
        }
        scm {
            url = "https://github.com/OCTestX/BasicMultiplatformLib"
            connection = "scm:git:git://github.com/OCTestX/BasicMultiplatformLib.git"
            developerConnection = "scm:git:ssh://github.com/OCTestX/BasicMultiplatformLib.git"
        }
    }
}
