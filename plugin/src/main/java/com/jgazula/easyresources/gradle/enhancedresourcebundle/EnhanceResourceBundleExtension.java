package com.jgazula.easyresources.gradle.enhancedresourcebundle;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

import javax.inject.Inject;

/**
 * The extension which captures the configuration necessary to execute the {@link EnhanceResourceBundleTask} task.
 */
public abstract class EnhanceResourceBundleExtension {

    public static final String EXTENSION_NAME = "enhanceResourceBundle";

    private final String name;

    @SuppressWarnings("InjectOnConstructorOfAbstractClass")
    @Inject
    public EnhanceResourceBundleExtension(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract DirectoryProperty getBundlePath();

    public abstract Property<String> getBundleName();

    public abstract Property<String> getGeneratedPackageName();

    public abstract Property<String> getGeneratedClassName();
}
