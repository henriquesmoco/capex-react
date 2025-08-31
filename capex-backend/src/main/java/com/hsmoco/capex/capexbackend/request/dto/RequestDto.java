package com.hsmoco.capex.capexbackend.request.dto;

import com.hsmoco.capex.capexbackend.request.model.RequestType;

import java.time.LocalDate;

public record RequestDto(Long id,
                         RequestType type,
                         String requestNumber,
                         String projectName,
                         LocalDate projectDate,
                         String description,
                         Double capexCost,
                         Double opexCost,
                         Boolean emergency,
                         Boolean itProject,
                         RequestNumberProjectionDto parent,
                         CategoryDto category,
                         BusinessUnitDto businessUnit) {
}