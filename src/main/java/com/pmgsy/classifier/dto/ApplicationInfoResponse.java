package com.pmgsy.classifier.dto;

import lombok.Builder;

@Builder
public record ApplicationInfoResponse(String application, String version, String status) {
}