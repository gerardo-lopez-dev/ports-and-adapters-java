package com.example.portsandadapters.infrastructure.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayMultiSchemaConfig {

    @Bean
    public CommandLineRunner runFlywayMigrations(DataSource dataSource, FlywayProperties properties) {
        return args -> {
            for (String schema : properties.schemas()) {
                Flyway flyway = Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration/" + schema)
                        .schemas(schema)
                        .load();

                flyway.migrate();
            }
        };
    }
}
