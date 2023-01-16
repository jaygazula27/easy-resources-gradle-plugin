package com.jgazula.easyresources.gradle.propertiesconstants;

import com.jgazula.easyresources.core.propertiesconstants.PropertiesConstantsConfig;
import com.jgazula.easyresources.core.propertiesconstants.PropertiesConstantsFileConfig;
import com.jgazula.easyresources.core.propertiesconstants.PropertiesConstants;
import com.jgazula.easyresources.core.util.ValidationException;
import com.jgazula.easyresources.gradle.Constants;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.util.Collections;

/**
 * The task that will generate sources which consist of constant fields that point to the keys of properties files.
 * This will allow developers to read data from properties files in a relatively typesafe manner.
 */
public abstract class PropertiesConstantsTask extends DefaultTask {

    public static final String TASK_NAME = "propertiesConstants";

    @Input
    public abstract Property<String> getCustomName();

    @InputFile
    public abstract RegularFileProperty getFile();

    @Input
    public abstract Property<String> getGeneratedPackageName();

    @Input
    public abstract Property<String> getGeneratedClassName();

    @OutputDirectory
    public abstract RegularFileProperty getGeneratedSourcesDir();

    @TaskAction
    public void execute() {
        try {
            var config = PropertiesConstantsConfig.builder()
                    .generatedBy(Constants.PLUGIN_NAME)
                    .fileConfigs(Collections.singletonList(toPCFileConfig()))
                    .destinationDir(getGeneratedSourcesDir().getAsFile().get().toPath())
                    .build();

            PropertiesConstants.create(config).generate();
        } catch (ValidationException e) {
            throw new GradleException("Validation error when generating constants for properties file: " +
                    getFile().getAsFile().get(), e);
        } catch (Exception e) {
            throw new GradleException("Unexpected error when generating constants for properties file: " +
                    getFile().getAsFile().get(), e);
        }

    }

    private PropertiesConstantsFileConfig toPCFileConfig() {
        return PropertiesConstantsFileConfig.builder()
                .propertiesPath(getFile().getAsFile().get().toPath())
                .generatedClassName(getGeneratedClassName().get())
                .generatedPackageName(getGeneratedPackageName().get())
                .build();
    }
}
