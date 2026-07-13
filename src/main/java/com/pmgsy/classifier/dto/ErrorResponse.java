package com.pmgsy.classifier.dto;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details) {
}
