package com.jgazula.gradle;

import com.jgazula.gradle.propertiesconstants.PropertiesConstantsExtension;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;

public abstract class TypesafeResourcesExtension implements ExtensionAware {

  public static final String EXTENSION_NAME = "typesafeResources";

  public TypesafeResourcesExtension() {
    System.out.println("in TypesafeResourcesExtension constructor");
//    getExtensions()
//        .create(PropertiesConstantsExtension.EXTENSION_NAME, PropertiesConstantsExtension.class);
  }

  @Nested
  public abstract PropertiesConstantsExtension getPropertiesConstants();
}
