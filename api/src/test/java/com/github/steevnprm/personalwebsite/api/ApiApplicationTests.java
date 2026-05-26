package com.github.steevnprm.personalwebsite.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "APP_JWT_SECRET=test_secret_qui_doit_etre_assez_long_pour_le_test_123456",
    "APP_JWT_EXPIRATION=86400",
    "APP_ADMIN_USERNAME=admin",
    "APP_ADMIN_PASSWORD=password",
    "SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "SPRING_DATASOURCE_USERNAME=sa",
    "SPRING_DATASOURCE_PASSWORD=",
    "SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver",
    "SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect",
    "SPRING_JPA_HIBERNATE_DDL_AUTO=create-drop"
})
class ApiApplicationTests {
    @Test
    void contextLoads() {
    }
}