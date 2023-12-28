package com.jgazula.easyresources.gradle.enhancedresourcebundle;

import com.jgazula.easyresources.core.enhancedresourcebundle.ERBBundleConfig;
import com.jgazula.easyresources.core.enhancedresourcebundle.ERBConfig;
import com.jgazula.easyresources.core.enhancedresourcebundle.EnhancedResourceBundle;
import com.jgazula.easyresources.core.util.ValidationException;
import com.jgazula.easyresources.gradle.Constants;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.util.Collections;

/**
 * The task that will generate sources which consist of enhanced resource bundles.
 * This will allow developers to read data from resource bundles in a relatively typesafe manner.
 */
public abstract class EnhanceResourceBundleTask extends DefaultTask {

    public static final String TASK_NAME = "enhanceResourceBundle";

    @InputDirectory
    public abstract DirectoryProperty getBundlePath();

    @Input
    public abstract Property<String> getBundleName();

    @Input
    public abstract Property<String> getGeneratedPackageName();

    @Input
    public abstract Property<String> getGeneratedClassName();

    @OutputDirectory
    public abstract DirectoryProperty getGeneratedSourcesDir();

    @TaskAction
    public void execute() {
        try {
            var config = ERBConfig.builder()
                    .generatedBy(Constants.PLUGIN_NAME)
                    .bundleConfigs(Collections.singletonList(toBundleConfig()))
                    .destinationDir(getGeneratedSourcesDir().getAsFile().get().toPath())
                    .build();

            EnhancedResourceBundle.create(config).generate();
        } catch (ValidationException e) {
            throw new GradleException("Validation error when enhancing resource bundle: " +
                    getBundleName().get(), e);
        } catch (Exception e) {
            throw new GradleException("Unexpected error when enhancing resource bundle: " +
                    getBundleName().get(), e);
        }
    }

    private ERBBundleConfig toBundleConfig() {
        return ERBBundleConfig.builder()
                .bundlePath(getBundlePath().getAsFile().get().toPath())
                .bundleName(getBundleName().get())
                .generatedPackageName(getGeneratedPackageName().get())
                .generatedClassName(getGeneratedClassName().get())
                .build();
    }
}
