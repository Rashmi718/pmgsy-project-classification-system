package com.pmgsy.classifier.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;

@Builder
public record PredictionAudit(
        String stateName,
        String districtName,
        String predictedScheme,
        Double confidence,
        BigDecimal modelScore,
        OffsetDateTime createdAt) {
}
