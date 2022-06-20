package com.jgazula.gradle;

import com.jgazula.gradle.propertiesconstants.PropertiesConstantsExtension;
import com.jgazula.gradle.propertiesconstants.PropertiesConstantsTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;

public class TypesafeResourcesPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    TypesafeResourcesExtension extension = project.getExtensions()
            .create(TypesafeResourcesExtension.EXTENSION_NAME, TypesafeResourcesExtension.class);

    registerPropertiesConstantsTask(project, extension);
  }

  private static void registerPropertiesConstantsTask(Project project,
      TypesafeResourcesExtension extension) {
    TaskProvider<PropertiesConstantsTask> taskProvider = project.getTasks()
        .register(PropertiesConstantsTask.TASK_NAME, PropertiesConstantsTask.class, task -> {
          System.out.print("huh");
          System.out.print(extension.getPropertiesConstants());
          task.getPropertiesConstants().set(extension.getPropertiesConstants());
        });

    project.getTasks().withType(JavaCompile.class).configureEach(task -> task.dependsOn(taskProvider));
  }
}
