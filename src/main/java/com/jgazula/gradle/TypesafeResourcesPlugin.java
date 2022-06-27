package com.jgazula.gradle;

import com.jgazula.gradle.propertiesconstants.PropertiesConstantsExtension;
import com.jgazula.gradle.propertiesconstants.PropertiesConstantsTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;

public class TypesafeResourcesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TypesafeResourcesExtension extension = project.getExtensions()
                .create(TypesafeResourcesExtension.EXTENSION_NAME, TypesafeResourcesExtension.class);

        registerPropertiesConstantsTask(project, extension);
    }

    private static void registerPropertiesConstantsTask(Project project,
                                                        TypesafeResourcesExtension typesafeResourcesExtension) {
        var objectFactory = project.getObjects();

        var propertiesConstantsContainer =
                objectFactory.domainObjectContainer(PropertiesConstantsExtension.class,
                        name -> objectFactory.newInstance(PropertiesConstantsExtension.class, name));

        typesafeResourcesExtension.getExtensions()
                .add(PropertiesConstantsExtension.EXTENSION_NAME, propertiesConstantsContainer);

        propertiesConstantsContainer.all(extension -> {
            var taskName = String.format("%s_%s", PropertiesConstantsTask.TASK_NAME, extension.getName());
            var pcTask = project.getTasks().register(taskName, PropertiesConstantsTask.class, task -> {
                task.getCustomName().set(extension.getName());
                task.getFile().set(extension.getFile());
                task.getGeneratedPackageName().set(extension.getGeneratedPackageName());
                task.getGeneratedClassName().set(extension.getGeneratedClassName());
            });

            project.getTasks().withType(JavaCompile.class).configureEach(task -> task.dependsOn(pcTask));
        });
    }
}
