# easy-resources-gradle-plugin

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/jaygazula27/easy-resources-gradle-plugin/gradle-build.yml)](https://github.com/jaygazula27/easy-resources-gradle-plugin/actions/workflows/gradle-build.yml)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/com.jgazula.easy-resources)](https://plugins.gradle.org/plugin/com.jgazula.easy-resources)
[![GitHub](https://img.shields.io/github/license/jaygazula27/easy-resources-gradle-plugin)](LICENSE)


# Table of contents

* [Overview](#overview)
* [Requirements](#requirements)
* [Usage](#usage)
  * [propertiesConstants](#propertiesconstants)
  * [enhanceResourceBundle](#enhanceResourceBundle)
* [Development](#development)
* [License](#license)


## Overview

This gradle plugin enables easy and typesafe access to resources. This is achieved by generating code with the following features:
* Classes consisting of `static final` fields which point to the keys of properties files.
* Classes consisting of methods with the appropriate parameters for dynamic internationalization which allows for lookup of localized strings.

Release notes are available in the [CHANGELOG](CHANGELOG.md) file.

For the maven alternative of this plugin, take a look at [easy-resources-maven-plugin](https://github.com/jaygazula27/easy-resources-maven-plugin).


## Requirements

* Java 11 or higher


## Usage

In the `build.gradle.kts` file of your gradle project, add the following to the `plugins` block:

```kotlin
plugins {
    id("com.jgazula.easy-resources") version "0.2.0"
}
```

This will create the `easyResources` DSL configuration container. It will contain the extensions which can be used
to configure the various tasks of this plugin. 

### propertiesConstants

The `propertiesConstants` extension generates classes with `static final` fields which point to the keys of properties files.

Parameters:

| Name                 | Description                                        |
|----------------------|----------------------------------------------------|
| file                 | The properties file to parse.                      |
| generatedPackageName | The java package the generated class should be in. |
| generatedClassName   | The name to use for the generated class.           |

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

### enhanceResourceBundle

The `enhanceResourceBundle` extension generates classes with methods that correspond to the keys of the properties in a resource bundle.

Parameters:

| Name                 | Description                                                                |
|----------------------|----------------------------------------------------------------------------|
| bundlePath           | The directory in which the resource bundle's properties files are located. |
| bundleName           | The name of the resource bundle.                                           |
| generatedPackageName | The java package the generated class should be in.                         |
| generatedClassName   | The name to use for the generated class.                                   |

Example:

```kotlin
easyResources {
  enhanceResourceBundle {
    create("AppBundle") {
      bundlePath.set(File("src/main/resources/"))
      bundleName.set("AppBundle")
      generatedPackageName.set("com.jgazula")
      generatedClassName.set("AppResourceBundle")
    }
  }
}
```

If we have a `AppBundle.properties` file with the following contents...

```properties
button.continue=Continue
class.final.grade=His final grade for {0} class was {1,number,percent}.
planet.quantity=There are {0,number,integer} planets.
monthly.payment=The monthly {1} bill is typically over {0,number,currency}.
```

as well as a `AppBundle_fr_FR.properties` file with the following contents...

```properties
date.of.birth=The applicant''s DOB is {0,date}.
store.opening.time=The {0} store opens every day at {1,time}.
files.quantity=There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.
```

...this would be the generated class:

```java
package com.jgazula;

import java.lang.Object;
import java.lang.String;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AppResourceBundle {
    private final ResourceBundle resourceBundle;

    public AppResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String buttonContinue() {
      String message = this.resourceBundle.getString("button.continue");
      return message;
    }

    public String classFinalGrade(String arg0, BigDecimal arg1) {
        String message = this.resourceBundle.getString("class.final.grade");
        Object[] messageArguments = {arg0, arg1};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String planetQuantity(int arg0) {
      String message = this.resourceBundle.getString("planet.quantity");
      Object[] messageArguments = {arg0};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String monthlyPayment(BigDecimal arg0, String arg1) {
      String message = this.resourceBundle.getString("monthly.payment");
      Object[] messageArguments = {arg0, arg1};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String dateOfBirth(Date arg0) {
        String message = this.resourceBundle.getString("date.of.birth");
        Object[] messageArguments = {arg0};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String storeOpeningTime(String arg0, Date arg1) {
      String message = this.resourceBundle.getString("store.opening.time");
      Object[] messageArguments = {arg0, arg1};
      return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }

    public String filesQuantity(int arg0) {
        String message = this.resourceBundle.getString("files.quantity");
        Object[] messageArguments = {arg0};
        return new MessageFormat(message, this.resourceBundle.getLocale()).format(messageArguments);
    }
}
```

As seen above in the generated class, the methods correspond to property keys of the resource bundle. 
Additionally, the parameters of these methods allow for formatting messages in a typesafe manner.

In order to use the generated class, the developer simply needs to construct an instance by passing in the appropriate resource bundle.
Since the instance of this generated class is thread safe, it can then be exposed as a bean and injected if using a framework such Spring, Quarkus, etc.


## Development

* Requires Java 11.
* The parsing and class generation logic is implemented in a dependency of this project: [easy-resources-core](https://github.com/jaygazula27/easy-resources-core)
* To build the project (and run unit and e2e tests): `./gradlw clean build`


## License

MIT License. Please see the [LICENSE](LICENSE) file for more information.