import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"));
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.0");
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.0");
    testImplementation ("org.mockito:mockito-core:3.6.0");
    testImplementation ("org.mockito:mockito-junit-jupiter:3.6.0");

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}