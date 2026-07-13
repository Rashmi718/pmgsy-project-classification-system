package com.pmgsy.classifier.dto;

import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record PredictionResponse(String prediction, Double confidence, OffsetDateTime timestamp) {
}
