package com.example.portsandadapters.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("flyway-test")
class FlywayMultiSchemaConfigTest {

    @Autowired
    DataSource dataSource;

    @Test
    void createsMigrationHistoryForAllSchemas() throws Exception {
        Set<String> tables = new HashSet<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_schema FROM information_schema.tables " +
                             "WHERE table_name = 'flyway_schema_history'")) {
            while (rs.next()) {
                tables.add(rs.getString("table_schema"));
            }
        }

        assertTrue(tables.contains("_shared"),
                "Expected _shared.flyway_schema_history to exist");
        assertTrue(tables.contains("auth"),
                "Expected auth.flyway_schema_history to exist");
    }

    @SpringBootApplication
    static class TestApp {
    }
}
