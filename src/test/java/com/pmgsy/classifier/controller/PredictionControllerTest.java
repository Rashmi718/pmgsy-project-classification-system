package com.pmgsy.classifier.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmgsy.classifier.dto.PredictionRequest;
import com.pmgsy.classifier.dto.PredictionResponse;
import com.pmgsy.classifier.service.PredictionService;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PredictionController.class)
class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PredictionService predictionService;

    @Test
    void predictReturnsOk() throws Exception {
        PredictionRequest request = buildRequest();
        PredictionResponse response = PredictionResponse.builder()
                .prediction("PMGSY-II")
                .confidence(0.97)
                .timestamp(OffsetDateTime.parse("2026-07-12T12:30:00Z"))
                .build();

        when(predictionService.predict(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/predict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prediction").value("PMGSY-II"))
                .andExpect(jsonPath("$.confidence").value(0.97));
    }

    @Test
    void predictRejectsInvalidBody() throws Exception {
        mockMvc.perform(post("/api/v1/predict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    private PredictionRequest buildRequest() {
        PredictionRequest request = new PredictionRequest();
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
        return request;
    }
}
