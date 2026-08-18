package com.example.portsandadapters.infrastructure.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("!test")
public class FlywayMultiSchemaConfig {

    @Bean
    public CommandLineRunner runFlywayMigrations(DataSource dataSource) {
        return args -> {
            List<String> schemas = List.of("_shared", "auth");

            for (String schema : schemas) {
                Flyway flyway = Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration/" + schema)
                        .schemas(schema)
                        .table("flyway_schema_history")
                        .load();

                flyway.migrate();
            }
        };
    }
}
