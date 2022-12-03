package com.jgazula.easyresources.gradle.propertiesconstants;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

/**
 * The extension which captures the configuration necessary to execute the {@link PropertiesConstantsTask} task.
 */
public abstract class PropertiesConstantsExtension {

    public static final String EXTENSION_NAME = "propertiesConstants";

    private final String name;

    @SuppressWarnings("InjectOnConstructorOfAbstractClass")
    @Inject
    public PropertiesConstantsExtension(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract RegularFileProperty getFile();

    public abstract Property<String> getGeneratedPackageName();

    public abstract Property<String> getGeneratedClassName();
}
