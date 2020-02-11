plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.61")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
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
