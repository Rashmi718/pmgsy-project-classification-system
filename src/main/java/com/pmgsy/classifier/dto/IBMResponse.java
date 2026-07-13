package com.pmgsy.classifier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record IBMResponse(List<Prediction> predictions) {

    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Prediction(List<String> fields, List<List<Object>> values) {
    }
}