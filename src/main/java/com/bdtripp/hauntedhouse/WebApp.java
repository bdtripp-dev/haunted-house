package com.bdtripp.hauntedhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Haunted House web application. 
 * 
 * This class starts a Spring Boot server.
 *
 * @author Brian Tripp
 * @version 2026.02.09
 */
@SpringBootApplication
public class WebApp
{
    /**
     * The main entry point of the web application. Starts a Spring Boot server.
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        SpringApplication.run(WebApp.class, args);
    }
}

