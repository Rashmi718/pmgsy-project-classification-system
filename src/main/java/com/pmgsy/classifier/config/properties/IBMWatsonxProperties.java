package com.pmgsy.classifier.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ibm.watsonx")
public record IBMWatsonxProperties(
        String apiKey,
        String deploymentEndpoint,
        String deploymentId,
        String apiVersion,
        String tokenUrl,
        int connectTimeoutMs,
        int readTimeoutMs) {
}