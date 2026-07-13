package com.pmgsy.classifier.mapper;

import com.pmgsy.classifier.dto.IBMRequest;
import com.pmgsy.classifier.dto.IBMResponse;
import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;
import com.pmgsy.classifier.exception.IBMWatsonxServiceException;
import com.pmgsy.classifier.util.DateTimeUtil;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PredictionMapper {

    public IBMRequest toIBMRequest(PredictionRequest request) {
        List<String> fields = List.of(
                "stateName",
                "districtName",
                "roadWorksSanctioned",
                "roadLengthSanctioned",
                "bridgesSanctioned",
                "costOfWorksSanctioned",
                "roadWorksCompleted",
                "roadLengthCompleted",
                "bridgesCompleted",
                "expenditureOccured",
                "roadWorksBalance",
                "roadLengthBalance",
                "bridgesBalance");

        List<Object> values = List.of(
                request.getStateName(),
                request.getDistrictName(),
                request.getRoadWorksSanctioned(),
                request.getRoadLengthSanctioned(),
                request.getBridgesSanctioned(),
                request.getCostOfWorksSanctioned(),
                request.getRoadWorksCompleted(),
                request.getRoadLengthCompleted(),
                request.getBridgesCompleted(),
                request.getExpenditureOccured(),
                request.getRoadWorksBalance(),
                request.getRoadLengthBalance(),
                request.getBridgesBalance());

        return IBMRequest.builder()
                .inputData(List.of(IBMRequest.InputData.builder()
                        .fields(fields)
                        .values(List.of(values))
                        .build()))
                .build();
    }

    public PredictionResponse toPredictionResponse(IBMResponse response) {
        if (response == null || response.predictions() == null || response.predictions().isEmpty()) {
            throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM deployment response was empty or invalid");
        }

        IBMResponse.Prediction prediction = response.predictions().get(0);
        if (prediction.values() == null || prediction.values().isEmpty() || prediction.values().get(0).isEmpty()) {
            throw new IBMWatsonxServiceException(HttpStatus.BAD_GATEWAY, "IBM deployment response did not contain prediction values");
        }

        List<Object> scoredValues = prediction.values().get(0);
        String predictedLabel = String.valueOf(scoredValues.get(0));
        Double confidence = scoredValues.size() > 1 ? toDouble(scoredValues.get(1)) : null;

        return PredictionResponse.builder()
                .prediction(predictedLabel)
                .confidence(confidence)
                .timestamp(DateTimeUtil.nowUtc())
                .build();
    }

    private Double toDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return new BigDecimal(String.valueOf(value)).doubleValue();
    }
}
