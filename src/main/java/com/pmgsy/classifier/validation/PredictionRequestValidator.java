package com.pmgsy.classifier.validation;

import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.util.ValidationUtils;
import org.springframework.stereotype.Component;

@Component
public class PredictionRequestValidator {

    public void validate(PredictionRequest request) {
        ValidationUtils.validatePredictionRequest(request);
    }
}