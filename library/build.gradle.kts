import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    kotlin("plugin.serialization") version "1.9.24"
    id("app.cash.sqldelight") version "2.0.2"
//    id("com.moriatsushi.cacheable") version "0.0.3"
}

group = "io.github.octestx"
version = "0.0.6R1"

kotlin {
    jvmToolchain(17)
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
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.coroutines.core)
                //
                implementation (libs.log4j.api)
                implementation (libs.log4j.core)
                implementation(libs.log4j.slf4j.impl)
                api(libs.klogging.jvm)
                //https://github.com/russhwolf/multiplatform-settings?tab=readme-ov-file#make-observable-module
                api(libs.multiplatform.settings)
                api(libs.multiplatform.settings.no.arg)
                //https://github.com/xxfast/KStore
                api(libs.kstore)
                api(libs.kstore.file)
                //https://github.com/syer10/Kotlin-Multiplatform-AppDirs
                api(libs.kotlin.multiplatform.appdirs)
                //https://sqldelight.github.io/sqldelight/2.0.2/

                //https://github.com/ReactiveCircus/cache4k
                api(libs.cache4k)
                //https://github.com/mori-atsushi/kotlin-cacheable
//                implementation(libs.cacheable.core)
                //https://github.com/InsertKoinIO/koin
                api(libs.koin.core)
                //
                api(libs.okio) // 请检查是否有更新版本
                api("org.jetbrains.kotlinx:kotlinx-io-core:0.7.0")
            }
        }
        jvmMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            api(libs.sqlite.driver)
        }
        androidMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            api(libs.android.driver)
            //https://github.com/InsertKoinIO/koin
            api(libs.koin.android)
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
        url = "https://github.com/OCTestX/BasicMultiplatformLib"
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
