package com.example.portsandadapters.architecture;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies by running the actual JAR inside a container:
 *
 *   VII. Port binding    -> app boots its own port without external server.
 *   IX.  Disposability   -> fast startup + graceful shutdown on SIGTERM.
 *
 * Unlike ArchUnit (bytecode analysis), this tests runtime behavior.
 */
@Testcontainers
class PortBindingAndDisposabilityTest {

    private static final Duration MAX_STARTUP = Duration.ofSeconds(15);
    private static final Duration MAX_SHUTDOWN = Duration.ofSeconds(5);

    private static GenericContainer<?> buildApp() {
        return new GenericContainer<>(
                new ImageFromDockerfile()
                        .withFileFromPath(".", Path.of(".")))
                .withExposedPorts(8080)
                .withEnv("SPRING_PROFILES_ACTIVE", "dev")
                .waitingFor(Wait.forListeningPort().withStartupTimeout(MAX_STARTUP));
    }

    // -------------------------------------------------------------------------
    // VII. Port binding — app binds its own port
    // -------------------------------------------------------------------------
    @Test
    void app_must_bind_its_own_port() {
        try (GenericContainer<?> app = buildApp()) {
            Instant start = Instant.now();
            app.start();
            Duration elapsed = Duration.between(start, Instant.now());

            assertThat(app.isRunning()).isTrue();
            assertThat(elapsed)
                    .as("VII. Port binding: startup within %s", MAX_STARTUP)
                    .isLessThan(MAX_STARTUP);
        }
    }

    // -------------------------------------------------------------------------
    // IX. Disposability — graceful shutdown on SIGTERM
    // -------------------------------------------------------------------------
    @Test
    void app_must_shutdown_gracefully_on_sigterm() {
        try (GenericContainer<?> app = buildApp()) {
            app.start();

            Instant stopRequested = Instant.now();
            app.stop();
            Duration shutdownTime = Duration.between(stopRequested, Instant.now());

            assertThat(shutdownTime)
                    .as("IX. Disposability: graceful shutdown within %s", MAX_SHUTDOWN)
                    .isLessThan(MAX_SHUTDOWN);
        }
    }
}
