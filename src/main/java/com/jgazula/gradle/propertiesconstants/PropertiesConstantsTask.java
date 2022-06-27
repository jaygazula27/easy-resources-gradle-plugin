package com.jgazula.gradle.propertiesconstants;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

public abstract class PropertiesConstantsTask extends DefaultTask {

    public static final String TASK_NAME = "propertiesConstants";

    public PropertiesConstantsTask() {
        System.out.println("in PropertiesConstantsTask constructor");
    }

    @Input
    public abstract Property<String> getCustomName();

    @InputFile
    public abstract RegularFileProperty getFile();

    @Input
    public abstract Property<String> getGeneratedPackageName();

    @Input
    public abstract Property<String> getGeneratedClassName();

    @TaskAction
    public void execute() {
        System.out.println("inside execute for " + getCustomName());

        if (getFile().isPresent()) {
            System.out.println("getFile() is present");
            System.out.println(getFile().get());
        } else {
            System.out.println("getFile() is not present");
        }
    }
}
