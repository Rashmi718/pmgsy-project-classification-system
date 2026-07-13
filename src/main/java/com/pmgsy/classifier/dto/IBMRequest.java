package com.pmgsy.classifier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record IBMRequest(@JsonProperty("input_data") List<InputData> inputData) {

    @Builder
    public record InputData(List<String> fields, List<List<Object>> values) {
    }
}