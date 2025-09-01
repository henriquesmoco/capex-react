package com.hsmoco.capex.capexbackend.request.dto;

import com.hsmoco.capex.capexbackend.request.model.RequestType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record RequestCreateDto(@NotNull RequestType type,
                               @NotNull @Length(max = 50) String projectName,
                               LocalDate projectDate,
                               @Length(max=300) String description,
                               Double capexCost,
                               Double opexCost,
                               Boolean emergency,
                               Boolean itProject,
                               Long parentId,
                               Long categoryId,
                               Long businessUnitId) {
}