package com.jgazula.easyresources.gradle;

import org.gradle.api.plugins.ExtensionAware;

/**
 * Top level extension of the plugin. This will contain the extensions of all other tasks.
 */
public abstract class EasyResourcesExtension implements ExtensionAware {

    public static final String EXTENSION_NAME = "easyResources";
}
