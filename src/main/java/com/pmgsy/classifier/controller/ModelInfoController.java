package com.pmgsy.classifier.controller;

import com.pmgsy.classifier.dto.ModelInfoResponse;
import com.pmgsy.classifier.service.ModelInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Model Info")
public class ModelInfoController {

    private final ModelInfoService modelInfoService;

    @GetMapping("/model-info")
    @Operation(summary = "Model metadata")
    public ModelInfoResponse modelInfo() {
        return modelInfoService.getModelInfo();
    }
}
