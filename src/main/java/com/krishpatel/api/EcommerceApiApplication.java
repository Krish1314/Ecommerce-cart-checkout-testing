package com.krishpatel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 *
 * @SpringBootApplication is a shortcut that combines three annotations:
 *   - @Configuration   → marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration → tells Spring Boot to auto-configure beans
 *                                 based on dependencies (e.g. DataSource for PostgreSQL)
 *   - @ComponentScan   → scans this package and sub-packages for @Controller,
 *                         @Service, @Repository etc.
 *
 * When you run this class, Spring Boot starts an embedded Tomcat server
 * and makes your REST API available at http://localhost:8080.
 */
@SpringBootApplication
public class EcommerceApiApplication {

    public static void main(String[] args) {
        // SpringApplication.run() bootstraps the entire application:
        //   1. Creates the Spring application context (IoC container)
        //   2. Starts the embedded web server
        //   3. Scans for components and wires them together
        SpringApplication.run(EcommerceApiApplication.class, args);
    }
}
