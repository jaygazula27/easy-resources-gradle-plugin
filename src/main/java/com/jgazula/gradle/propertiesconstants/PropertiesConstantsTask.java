package com.jgazula.gradle.propertiesconstants;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

public abstract class PropertiesConstantsTask extends DefaultTask {

  public static final String TASK_NAME = "propertiesConstants";

  @Optional
  @Input
  public abstract Property<PropertiesConstantsExtension> getPropertiesConstants();

  @TaskAction
  public void execute() {
    System.out.println("inside execute");

    if (getPropertiesConstants().isPresent()) {
      System.out.println("propertiesConstants extension is present");
      System.out.println(getPropertiesConstants().get().getPropertiesFiles().get().size());
    } else {
      System.out.println("propertiesConstants extension is not present");
    }
  }
}
