* ./gradlew publishToMavenLocal

* Note the fact that unit tests call the E2E project.

* TODO: To get type completion in intellij automatically, we need this in build.gradle:
```
idea {
    module {
        sourceDirs += file("build/generated/sources/typesafe-resources/")
    }
}
```