package com.jgazula.typesaferesources.gradle;

import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypesafeResourcesPluginE2ELauncher {

    private static final Path E2E_PROJECT = Path.of("")
                                                .toAbsolutePath()
                                                .resolveSibling("e2e");

    private static final Path E2E_PROJECT_TEST_KIT_DIR = E2E_PROJECT.resolve(".gradle-test-kit");

    @Test
    public void checkE2EProject() {
        // TODO the unit tests in the E2E project are not executing.
        // https://discuss.gradle.org/t/testkit-downloading-dependencies/12305/2

        // when
        var result = GradleRunner.create()
                .withProjectDir(E2E_PROJECT.toFile())
                .withArguments("clean", "test")
                .withPluginClasspath()
                .withTestKitDir(E2E_PROJECT_TEST_KIT_DIR.toAbsolutePath().toFile())
                .build();

        // then
        var checkTask = result.task("check");
        System.out.println(result.getOutput());
        assertNotNull(checkTask);
        assertEquals(checkTask.getOutcome(), TaskOutcome.SUCCESS);
    }
}
