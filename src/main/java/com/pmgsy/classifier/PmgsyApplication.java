package com.pmgsy.classifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PmgsyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmgsyApplication.class, args);
    }
}
