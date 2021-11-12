buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.3.61")
    }
}

plugins {
    `java-library`
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.serialization") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    maven
}

group = "com.github.mmoghaddam385"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")

    val ktorVersion = "1.6.5"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")

    // Annotation processor that generates Java builders for data classes
    val ktBuilderVersion = "1.1.0"
    implementation("com.thinkinglogic.builder:kotlin-builder-annotation:$ktBuilderVersion")
    kapt("com.thinkinglogic.builder:kotlin-builder-processor:$ktBuilderVersion")

    testImplementation("junit:junit:4.12")
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    val sourcesJar by creating(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        classifier = "sources"
        from(sourceSets["main"].allSource)
    }

    artifacts {
        add("archives", sourcesJar)
        add("archives", jar)
    }
}