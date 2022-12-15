package com.jgazula.easyresources.gradle;

import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class EasyResourcesPluginE2ELauncher {

    private static final Path E2E_PROJECT = Path.of("")
                                                .toAbsolutePath()
                                                .resolveSibling("e2e");

    private static final Path E2E_PROJECT_TEST_KIT_DIR = E2E_PROJECT.resolve(".gradle-test-kit");

    @Test
    public void checkE2EProject() {
        // when
        var result = GradleRunner.create()
                .withProjectDir(E2E_PROJECT.toFile())
                .withArguments("clean", "check")
                .withPluginClasspath()
                .withTestKitDir(E2E_PROJECT_TEST_KIT_DIR.toAbsolutePath().toFile())
                .build();

        // then
        var checkTask = result.task(":check");
        assertThat(checkTask).isNotNull();
        assertThat(checkTask.getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
    }
}
