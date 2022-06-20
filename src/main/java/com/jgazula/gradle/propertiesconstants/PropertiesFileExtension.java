package com.jgazula.gradle.propertiesconstants;

import java.io.File;
import org.gradle.api.provider.Property;

public abstract class PropertiesFileExtension {

  public static final String EXTENSION_NAME = "propertiesFile";

  public abstract Property<File> getFile();

  public abstract Property<String> getGeneratedPackageName();

  public abstract Property<String> getGeneratedClassName();
}
