package com.jgazula.gradle.propertiesconstants;

import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.ListProperty;

public abstract class PropertiesConstantsExtension {

  public static final String EXTENSION_NAME = "propertiesConstants";

  public PropertiesConstantsExtension() {
    ((ExtensionAware) this).getExtensions()
        .create(PropertiesFileExtension.EXTENSION_NAME, PropertiesFileExtension.class);
  }

  public abstract ListProperty<PropertiesFileExtension> getPropertiesFiles();
}
