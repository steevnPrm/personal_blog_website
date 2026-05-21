package com.github.steevnprm.personalwebsite.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}

@Component
class JwtDebugPrinter implements CommandLineRunner {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void run(String... args) {
        System.out.println("JWT SECRET = " + jwtSecret);
    }
}