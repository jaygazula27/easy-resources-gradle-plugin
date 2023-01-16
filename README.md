# easy-resources-gradle-plugin

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/jaygazula27/easy-resources-gradle-plugin/gradle-build.yml)](https://github.com/jaygazula27/easy-resources-maven-plugin/actions/workflows/maven-build.yml)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/com.jgazula.easy-resources)](https://plugins.gradle.org/plugin/com.jgazula.easy-resources)
[![GitHub](https://img.shields.io/github/license/jaygazula27/easy-resources-gradle-plugin)](LICENSE)


# Table of contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Usage](#usage)
  * [propertiesConstants](#propertiesconstants)
* [Development](#development)
* [License](#license)


## Overview

This gradle plugin enables easy and typesafe access to resources. This is achieved by generating code with the following features:
* Classes consisting of `static final` fields which point to the keys of properties files.
* Classes consisting of methods with the appropriate parameters for dynamic internationalization which allows for lookup of localized strings.
    * Feature implementation in progress.

Release notes are available in the [CHANGELOG](CHANGELOG.md) file.

For the maven alternative of this plugin, take a look at [easy-resources-maven-plugin](https://github.com/jaygazula27/easy-resources-maven-plugin).


## Requirements

* Java 11 or higher


## Usage

In the `build.gradle.kts` file of your gradle project, add the following to the `plugins` block:

```kotlin
plugins {
    id("com.jgazula.easy-resources") version "0.1.0"
}
```

This will create the `easyResources` DSL configuration container. It will contain the extensions which can be used
to configure the various tasks of this plugin. 

### propertiesConstants

The `propertiesConstants` extension can be used to generate classes with `static final` fields which point to the keys of properties files.

Parameters:

| Name | Description                   |
| ---- |-------------------------------|
| file | The properties file to parse. |
| generatedPackageName | The package of the generated class. |
| generatedClassName | The name of the generated class. |

Example:

```kotlin
easyResources {
    propertiesConstants {
        create("database") {
            file.set(File("src/main/resources/database.properties"))
            generatedPackageName.set("com.jgazula")
            generatedClassName.set("DatabaseProperties")
        }
    }
}
```

If we have a `database.properties` file with the following contents...

```properties
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

...this would be the generated class:

```java
package com.jgazula;

import java.lang.String;

public class DatabaseProperties {
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
}
```


## Development

* Requires Java 11.
* The parsing and class generation logic is implemented in a dependency of this project: [easy-resources-core](https://github.com/jaygazula27/easy-resources-core)
* To build the project (and run unit and e2e tests): `./gradlw clean build`


## License

MIT License. Please see the [LICENSE](LICENSE) file for more information.