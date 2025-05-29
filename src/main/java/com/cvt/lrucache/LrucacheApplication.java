package com.cvt.lrucache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the LRU Cache service.
 * Enables auto-configuration and component scanning.
 */
@SpringBootApplication
public class LrucacheApplication {

    /**
     * Main method that starts the Spring Boot application.
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(LrucacheApplication.class, args);
    }
}
