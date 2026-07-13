package com.pmgsy.classifier.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pmgsy.classifier.dto.ModelInfoResponse;
import com.pmgsy.classifier.service.ModelInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ModelInfoController.class)
class ModelInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelInfoService modelInfoService;

    @Test
    void modelInfoReturnsMetadata() throws Exception {
        when(modelInfoService.getModelInfo()).thenReturn(ModelInfoResponse.builder()
                .modelName("Random Forest")
                .version("1.0")
                .algorithm("RandomForestClassifier")
                .features(13)
                .target("PMGSY_SCHEME")
                .build());

        mockMvc.perform(get("/api/v1/model-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName").value("Random Forest"))
                .andExpect(jsonPath("$.features").value(13));
    }
}
