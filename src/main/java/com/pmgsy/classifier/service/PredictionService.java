package com.pmgsy.classifier.service;

import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;

public interface PredictionService {

    PredictionResponse predict(PredictionRequest request);
}
