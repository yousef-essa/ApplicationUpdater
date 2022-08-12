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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    getByName<Test>("test") {
        useJUnitPlatform()
    }

    compileJava {
        sourceCompatibility = "8"
    }
}