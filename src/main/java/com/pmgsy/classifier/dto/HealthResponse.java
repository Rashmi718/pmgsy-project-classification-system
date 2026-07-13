package com.pmgsy.classifier.dto;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record HealthResponse(String status, OffsetDateTime timestamp) {
}