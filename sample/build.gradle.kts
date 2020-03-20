plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.61")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")

    // For pretty printing data classes
    implementation("com.tylerthrailkill.helpers:pretty-print:2.0.2")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "io.polygon.kotlin.sdk.sample.KotlinUsageSampleKt"
    }
}

task(name = "javaSample", type = JavaExec::class) {
    group = "samples"
    description = "Sample usage of the polygon client from Java"
    main = "io.polygon.kotlin.sdk.sample.JavaUsageSample"
    classpath = sourceSets["main"].runtimeClasspath
}

task(name = "kotlinSample", type = JavaExec::class) {
    group = "samples"
    description = "Sample usage of the polygon client from Kotlin"
    main = "io.polygon.kotlin.sdk.sample.KotlinUsageSampleKt"
    classpath = sourceSets["main"].runtimeClasspath
}
