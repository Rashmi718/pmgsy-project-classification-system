package com.pmgsy.classifier.service.impl;

import com.pmgsy.classifier.constants.AppConstants;
import com.pmgsy.classifier.dto.HealthResponse;
import com.pmgsy.classifier.service.HealthService;
import com.pmgsy.classifier.util.DateTimeUtil;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public HealthResponse getHealth() {
        return HealthResponse.builder()
                .status(AppConstants.STATUS_UP)
                .timestamp(DateTimeUtil.nowUtc())
                .build();
    }
}
