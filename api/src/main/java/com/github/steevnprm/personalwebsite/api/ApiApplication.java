package com.github.steevnprm.personalwebsite.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);
        
        // Diagnostic : affiche la valeur réelle vue par Spring
        Environment env = context.getEnvironment();
        System.out.println("--- DIAGNOSTIC DES VARIABLES ---");
        System.out.println("DB_URL détecté : " + env.getProperty("DB_URL"));
        System.out.println("DB_USERNAME détecté : " + env.getProperty("DB_USERNAME"));
    }
}