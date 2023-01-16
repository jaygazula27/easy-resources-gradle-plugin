import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("java-gradle-plugin")
    id("net.ltgt.errorprone") version "3.0.1"
    id("net.researchgate.release") version "3.0.2"
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = property("group").toString()
version = property("version").toString()

repositories {
    mavenCentral()
    mavenLocal()
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
    implementation("com.jgazula:easy-resources-core:0.1.0")

    errorprone("com.google.errorprone:error_prone_core:2.16")
    annotationProcessor("com.uber.nullaway:nullaway:0.10.5")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

release {
    tagTemplate.set("v\$version")
}

gradlePlugin {
    plugins {
        create(property("id").toString()) {
            id = property("id").toString()
            implementationClass = property("implementation.class").toString()
            version = property("version").toString()
        }
    }
}
