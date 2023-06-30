package com.learning.orderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public abstract class AbstractDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabase.class);

    static MySQLContainer DATABASE = new MySQLContainer("mysql:8.0")
            .withDatabaseName("saga-db")
            .withUsername("root")
            .withPassword("root");

    static {
        DATABASE.addEnv("MYSQL_ROOT_PASSWORD", "root");
        DATABASE.withLogConsumer(new Slf4jLogConsumer(LOGGER));
        DATABASE.start();
    }

    static void mariaProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
        registry.add("spring.datasource.password", DATABASE::getPassword);
        registry.add("spring.datasource.username", DATABASE::getUsername);
    }

}
