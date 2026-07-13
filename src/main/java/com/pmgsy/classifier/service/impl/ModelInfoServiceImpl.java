package com.pmgsy.classifier.service.impl;

import com.pmgsy.classifier.constants.AppConstants;
import com.pmgsy.classifier.dto.ModelInfoResponse;
import com.pmgsy.classifier.service.ModelInfoService;
import org.springframework.stereotype.Service;

@Service
public class ModelInfoServiceImpl implements ModelInfoService {

    @Override
    public ModelInfoResponse getModelInfo() {
        return ModelInfoResponse.builder()
                .modelName(AppConstants.MODEL_NAME)
                .version(AppConstants.MODEL_VERSION)
                .algorithm(AppConstants.MODEL_ALGORITHM)
                .features(AppConstants.MODEL_FEATURE_COUNT)
                .target(AppConstants.MODEL_TARGET)
                .build();
    }
}
