package com.pmgsy.classifier.util;

import com.pmgsy.classifier.dto.PredictionRequest;
import jakarta.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validatePredictionRequest(PredictionRequest request) {
        if (request == null) {
            throw new ValidationException("Prediction request cannot be null");
        }

        validateNotGreater(request.getRoadWorksCompleted(), request.getRoadWorksSanctioned(), "roadWorksCompleted", "roadWorksSanctioned");
        validateNotGreater(request.getRoadLengthCompleted(), request.getRoadLengthSanctioned(), "roadLengthCompleted", "roadLengthSanctioned");
        validateNotGreater(request.getBridgesCompleted(), request.getBridgesSanctioned(), "bridgesCompleted", "bridgesSanctioned");
        validateNotGreater(request.getExpenditureOccured(), request.getCostOfWorksSanctioned(), "expenditureOccured", "costOfWorksSanctioned");

        int roadWorksBalance = request.getRoadWorksSanctioned() - request.getRoadWorksCompleted();
        if (!Objects.equals(request.getRoadWorksBalance(), roadWorksBalance)) {
            throw new ValidationException("roadWorksBalance must equal roadWorksSanctioned - roadWorksCompleted");
        }

        BigDecimal expectedRoadLengthBalance = request.getRoadLengthSanctioned().subtract(request.getRoadLengthCompleted());
        if (request.getRoadLengthBalance().compareTo(expectedRoadLengthBalance) != 0) {
            throw new ValidationException("roadLengthBalance must equal roadLengthSanctioned - roadLengthCompleted");
        }

        int bridgeBalance = request.getBridgesSanctioned() - request.getBridgesCompleted();
        if (!Objects.equals(request.getBridgesBalance(), bridgeBalance)) {
            throw new ValidationException("bridgesBalance must equal bridgesSanctioned - bridgesCompleted");
        }
    }

    private static void validateNotGreater(Number completed, Number sanctioned, String completedField, String sanctionedField) {
        if (completed == null || sanctioned == null) {
            return;
        }

        if (completed.doubleValue() > sanctioned.doubleValue()) {
            throw new ValidationException(completedField + " cannot be greater than " + sanctionedField);
        }
    }
}