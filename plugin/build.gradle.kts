import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("java-gradle-plugin")
    id("net.ltgt.errorprone") version "3.0.1"
    id("maven-publish")
}

group = property("GROUP").toString()
version = property("VERSION").toString()

repositories {
    mavenCentral()
    mavenLocal()
}

publishing {
    repositories {
        maven {
            mavenLocal()
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableWarningsInGeneratedCode.set(true)
        check("NullAway", net.ltgt.gradle.errorprone.CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "com.jgazula")
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging.showStandardStreams = true
    }

    compileTestJava {
        options.errorprone.isEnabled.set(false)
    }
}

dependencies {
    implementation("com.jgazula:easy-resources-core:0.1.0-SNAPSHOT")

    errorprone("com.google.errorprone:error_prone_core:2.16")
    annotationProcessor("com.uber.nullaway:nullaway:0.10.5")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

gradlePlugin {
    plugins {
        create(property("ID").toString()) {
            id = property("ID").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            version = property("VERSION").toString()
        }
    }
}