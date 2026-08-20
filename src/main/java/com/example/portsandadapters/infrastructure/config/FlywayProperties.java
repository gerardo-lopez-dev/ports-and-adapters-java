package com.example.portsandadapters.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.flyway")
public record FlywayProperties(List<String> schemas) {
}
