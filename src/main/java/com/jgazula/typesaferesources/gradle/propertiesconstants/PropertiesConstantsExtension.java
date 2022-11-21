package com.jgazula.typesaferesources.gradle.propertiesconstants;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

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
