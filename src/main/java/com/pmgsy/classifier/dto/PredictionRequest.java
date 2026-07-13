package com.pmgsy.classifier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Schema(name = "PredictionRequest", description = "Features for PMGSY scheme prediction")
public class PredictionRequest {

    @NotBlank
    @Schema(example = "Odisha")
    private String stateName;

    @NotBlank
    @Schema(example = "Khordha")
    private String districtName;

    @NotNull
    @PositiveOrZero
    @Schema(example = "15")
    private Integer roadWorksSanctioned;

    @NotNull
    @PositiveOrZero
    @Schema(example = "12.5")
    private BigDecimal roadLengthSanctioned;

    @NotNull
    @PositiveOrZero
    @Schema(example = "2")
    private Integer bridgesSanctioned;

    @NotNull
    @PositiveOrZero
    @Schema(example = "5000000")
    private BigDecimal costOfWorksSanctioned;

    @NotNull
    @PositiveOrZero
    @Schema(example = "12")
    private Integer roadWorksCompleted;

    @NotNull
    @PositiveOrZero
    @Schema(example = "10")
    private BigDecimal roadLengthCompleted;

    @NotNull
    @PositiveOrZero
    @Schema(example = "1")
    private Integer bridgesCompleted;

    @NotNull
    @PositiveOrZero
    @Schema(example = "4500000")
    private BigDecimal expenditureOccured;

    @NotNull
    @PositiveOrZero
    @Schema(example = "3")
    private Integer roadWorksBalance;

    @NotNull
    @PositiveOrZero
    @Schema(example = "2.5")
    private BigDecimal roadLengthBalance;

    @NotNull
    @PositiveOrZero
    @Schema(example = "1")
    private Integer bridgesBalance;
}
