plugins {
    java
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "io.yousefessa"
version = "0.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.googlecode.json-simple:json-simple:1.1")
    implementation("org.tinylog:tinylog-api-kotlin:2.5.0")
    implementation("org.tinylog:tinylog-impl:2.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.mockito:mockito-core:3.+")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks {
    getByName<Test>("test") {
        useJUnitPlatform()
    }

    compileJava {
        sourceCompatibility = "8"
    }
}