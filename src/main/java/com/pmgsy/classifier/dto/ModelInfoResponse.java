package com.pmgsy.classifier.dto;

import lombok.Builder;

@Builder
public record ModelInfoResponse(String modelName, String version, String algorithm, int features, String target) {
}
