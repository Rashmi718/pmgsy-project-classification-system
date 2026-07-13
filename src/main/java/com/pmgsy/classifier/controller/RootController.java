package com.pmgsy.classifier.controller;

import com.pmgsy.classifier.config.properties.AppProperties;
import com.pmgsy.classifier.constants.AppConstants;
import com.pmgsy.classifier.dto.ApplicationInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Application")
public class RootController {

    private final AppProperties appProperties;

    @GetMapping("/")
    @Operation(summary = "Application information")
    public ApplicationInfoResponse applicationInfo() {
        return ApplicationInfoResponse.builder()
                .application(appProperties.name())
                .version(appProperties.version())
                .status(appProperties.status() != null ? appProperties.status() : AppConstants.STATUS_RUNNING)
                .build();
    }
}
