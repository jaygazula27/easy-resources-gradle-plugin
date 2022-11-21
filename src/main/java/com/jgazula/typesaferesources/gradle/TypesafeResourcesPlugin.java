package com.jgazula.typesaferesources.gradle;

import com.jgazula.typesaferesources.gradle.propertiesconstants.PropertiesConstantsExtension;
import com.jgazula.typesaferesources.gradle.propertiesconstants.PropertiesConstantsTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;

import java.nio.file.Path;

public class TypesafeResourcesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        TypesafeResourcesExtension extension = project.getExtensions()
                .create(TypesafeResourcesExtension.EXTENSION_NAME, TypesafeResourcesExtension.class);

        registerPropertiesConstantsTask(project, extension);

        addGeneratedDirAsCompileTarget(project);
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
                task.getGeneratedSourcesDir().set(generatedSourcesDir(project).toFile());
            });

            project.getTasks().withType(JavaCompile.class).configureEach(task -> task.dependsOn(pcTask));
        });
    }

    private static Path generatedSourcesDir(Project project) {
        return Path.of(project.getBuildDir().getAbsolutePath(), "generated", "sources", "typesafe-resources", "main",
                "java");
    }

    private static void addGeneratedDirAsCompileTarget(Project project) {
        var generatedSrcDir = Path.of(generatedSourcesDir(project).toString(), "**");
        project.getTasks().withType(JavaCompile.class).configureEach(t -> {
            t.getIncludes().add(generatedSrcDir.toString());
        });
    }
}
