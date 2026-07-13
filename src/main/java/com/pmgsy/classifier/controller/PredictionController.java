package com.pmgsy.classifier.controller;

import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;
import com.pmgsy.classifier.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Prediction")
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping("/predict")
    @Operation(summary = "Predict PMGSY scheme", description = "Predicts the likely PMGSY scheme using a Python ML microservice.")
    @ApiResponse(responseCode = "200", description = "Successful prediction")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = PredictionRequest.class),
                    examples = @ExampleObject(
                            name = "Sample prediction request",
                            value = """
                                    {
                                      "stateName":"Odisha",
                                      "districtName":"Khordha",
                                      "roadWorksSanctioned":15,
                                      "roadLengthSanctioned":12.5,
                                      "bridgesSanctioned":2,
                                      "costOfWorksSanctioned":5000000,
                                      "roadWorksCompleted":12,
                                      "roadLengthCompleted":10,
                                      "bridgesCompleted":1,
                                      "expenditureOccured":4500000,
                                      "roadWorksBalance":3,
                                      "roadLengthBalance":2.5,
                                      "bridgesBalance":1
                                    }
                                    """)))
    public ResponseEntity<PredictionResponse> predict(@Valid @RequestBody PredictionRequest request) {
        log.info("Incoming prediction request for state={}, district={}", request.getStateName(), request.getDistrictName());
        return ResponseEntity.ok(predictionService.predict(request));
    }
}