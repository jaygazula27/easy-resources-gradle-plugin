plugins {
    id("java")
    id("com.jgazula.typesafe-resources")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

typesafeResources {
    propertiesConstants {

    }
}

typesafeResources {
    propertiesConstants {
        create("database") {
            file.set(File("src/main/resources/database.properties"))
            generatedPackageName.set("com.jgazula")
            generatedClassName.set("Database")
        }

        create("application") {
            file.set(File("src/main/resources/main.properties"))
            generatedPackageName.set("com.jgazula")
            generatedClassName.set("Main")
        }
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.22.0")
}