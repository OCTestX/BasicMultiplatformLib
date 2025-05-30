import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    kotlin("plugin.serialization") version "1.9.24"
}

group = "io.github.octestx"
version = "0.1.3"

kotlin {
    jvmToolchain(17)
    jvm()
    androidTarget {
        publishLibraryVariants("release")
    }

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
                api(libs.kotlinx.io.core)
                api("org.ktorm:ktorm-core:4.1.1")
                api("org.ktorm:ktorm-support-sqlite:4.1.1")
            }
        }
        jvmMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            implementation("org.xerial:sqlite-jdbc:3.45.2.0")
        }
        androidMain.dependencies {
            //https://sqldelight.github.io/sqldelight/2.0.2/
            implementation("org.xerial:sqlite-jdbc:3.45.2.0")
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
