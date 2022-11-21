package com.jgazula.typesaferesources.gradle.propertiesconstants;

import com.jgazula.typesaferesources.core.propertiesconstants.PCConfig;
import com.jgazula.typesaferesources.core.propertiesconstants.PCFileConfig;
import com.jgazula.typesaferesources.core.propertiesconstants.PropertiesConstants;
import com.jgazula.typesaferesources.core.util.ValidationException;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.util.Collections;

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
            var config = PCConfig.builder()
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

    private PCFileConfig toPCFileConfig() {
        return PCFileConfig.builder()
                .propertiesPath(getFile().getAsFile().get().toPath())
                .generatedClassName(getGeneratedClassName().get())
                .generatedPackageName(getGeneratedPackageName().get())
                .build();
    }
}
