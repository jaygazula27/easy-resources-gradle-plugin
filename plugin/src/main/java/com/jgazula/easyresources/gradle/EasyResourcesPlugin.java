package com.jgazula.easyresources.gradle;

import com.jgazula.easyresources.gradle.propertiesconstants.PropertiesConstantsExtension;
import com.jgazula.easyresources.gradle.propertiesconstants.PropertiesConstantsTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.compile.JavaCompile;

import java.nio.file.Path;

public class EasyResourcesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // Always automatically apply the Java plugin.
        project.getPluginManager().apply(JavaPlugin.class);

        EasyResourcesExtension extension = project.getExtensions()
                .create(EasyResourcesExtension.EXTENSION_NAME, EasyResourcesExtension.class);
        registerPropertiesConstantsTask(project, extension);

        addGeneratedDirAsCompileTarget(project);
    }

    private static void registerPropertiesConstantsTask(Project project,
                                                        EasyResourcesExtension easyResourcesExtension) {
        var objectFactory = project.getObjects();

        var propertiesConstantsContainer =
                objectFactory.domainObjectContainer(PropertiesConstantsExtension.class,
                        name -> objectFactory.newInstance(PropertiesConstantsExtension.class, name));

        easyResourcesExtension.getExtensions()
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
        return Path.of(project.getBuildDir().getAbsolutePath(), "generated", "sources", "easy-resources", "main",
                "java");
    }

    private static void addGeneratedDirAsCompileTarget(Project project) {
        project.getExtensions().getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getJava()
                .srcDir(generatedSourcesDir(project));
    }
}
