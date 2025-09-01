package com.hsmoco.capex.capexbackend.request.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record RequestEditDto(@NotNull Long id,
                             @NotNull @Length(max = 50) String projectName,
                             LocalDate projectDate,
                             @Length(max=300) String description,
                             Double capexCost,
                             Double opexCost,
                             Boolean emergency,
                             Boolean itProject,
                             Long categoryId,
                             Long businessUnitId) {
}