plugins {
    java
    kotlin("jvm") version "1.6.10"
    "maven-publish"
}

group = "io.yousefessa"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.googlecode.json-simple:json-simple:1.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.mockito:mockito-core:3.+")
}

tasks {
    getByName<Test>("test") {
        useJUnitPlatform()
    }

    compileJava {
        sourceCompatibility = "8"
    }
}