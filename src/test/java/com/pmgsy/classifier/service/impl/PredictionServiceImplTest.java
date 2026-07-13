package com.pmgsy.classifier.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pmgsy.classifier.dto.IBMRequest;
import com.pmgsy.classifier.dto.IBMResponse;
import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;
import com.pmgsy.classifier.mapper.PredictionMapper;
import com.pmgsy.classifier.service.IBMWatsonxPredictionService;
import com.pmgsy.classifier.validation.PredictionRequestValidator;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PredictionServiceImplTest {

    @Mock
    private IBMWatsonxPredictionService ibmWatsonxPredictionService;

    @Mock
    private PredictionMapper predictionMapper;

    @Mock
    private PredictionRequestValidator validator;

    @InjectMocks
    private PredictionServiceImpl predictionService;

    private PredictionRequest request;

    @BeforeEach
    void setUp() {
        request = new PredictionRequest();
        request.setStateName("Odisha");
        request.setDistrictName("Khordha");
        request.setRoadWorksSanctioned(15);
        request.setRoadLengthSanctioned(BigDecimal.valueOf(12.5));
        request.setBridgesSanctioned(2);
        request.setCostOfWorksSanctioned(BigDecimal.valueOf(5000000));
        request.setRoadWorksCompleted(12);
        request.setRoadLengthCompleted(BigDecimal.valueOf(10));
        request.setBridgesCompleted(1);
        request.setExpenditureOccured(BigDecimal.valueOf(4500000));
        request.setRoadWorksBalance(3);
        request.setRoadLengthBalance(BigDecimal.valueOf(2.5));
        request.setBridgesBalance(1);
    }

    @Test
    void predictReturnsMappedResponse() {
        IBMRequest ibmRequest = IBMRequest.builder().build();
        IBMResponse ibmResponse = IBMResponse.builder()
            .predictions(java.util.List.of(
                IBMResponse.Prediction.builder()
                    .fields(java.util.List.of("prediction", "confidence"))
                    .values(java.util.List.of(java.util.List.of("PMGSY-II", 0.97)))
                    .build()))
            .build();

        when(predictionMapper.toIBMRequest(request)).thenReturn(ibmRequest);
        when(ibmWatsonxPredictionService.predict(ibmRequest)).thenReturn(ibmResponse);
        when(predictionMapper.toPredictionResponse(ibmResponse)).thenReturn(
            PredictionResponse.builder().prediction("PMGSY-II").confidence(0.97).timestamp(java.time.OffsetDateTime.parse("2026-07-12T12:30:00Z")).build());

        PredictionResponse response = predictionService.predict(request);

        assertEquals("PMGSY-II", response.prediction());
        assertEquals(0.97, response.confidence());
        verify(validator).validate(request);
        verify(predictionMapper).toIBMRequest(request);
        verify(ibmWatsonxPredictionService).predict(ibmRequest);
    }

    @Test
    void predictPropagatesValidationErrors() {
        RuntimeException validationError = new RuntimeException("bad request");
        org.mockito.Mockito.doThrow(validationError).when(validator).validate(request);

        assertThrows(RuntimeException.class, () -> predictionService.predict(request));
    }
}
