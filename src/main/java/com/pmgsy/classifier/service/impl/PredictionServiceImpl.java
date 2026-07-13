package com.pmgsy.classifier.service.impl;

import com.pmgsy.classifier.dto.IBMRequest;
import com.pmgsy.classifier.dto.IBMResponse;
import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;
import com.pmgsy.classifier.mapper.PredictionMapper;
import com.pmgsy.classifier.service.IBMWatsonxPredictionService;
import com.pmgsy.classifier.service.PredictionService;
import com.pmgsy.classifier.validation.PredictionRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final IBMWatsonxPredictionService ibmWatsonxPredictionService;
    private final PredictionMapper predictionMapper;
    private final PredictionRequestValidator validator;

    @Override
    public PredictionResponse predict(PredictionRequest request) {
        log.info("Processing prediction request for state={}, district={}", request.getStateName(), request.getDistrictName());
        validator.validate(request);
        IBMRequest ibmRequest = predictionMapper.toIBMRequest(request);
        IBMResponse ibmResponse = ibmWatsonxPredictionService.predict(ibmRequest);
        PredictionResponse response = predictionMapper.toPredictionResponse(ibmResponse);
        log.info("Prediction generated: {} with confidence {}", response.prediction(), response.confidence());
        return response;
    }
}
