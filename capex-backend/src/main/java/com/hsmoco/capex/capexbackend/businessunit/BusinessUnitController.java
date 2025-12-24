package com.hsmoco.capex.capexbackend.businessunit;

import com.hsmoco.capex.capexbackend.businessunit.dto.BusinessUnitDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/businessunits")
public class BusinessUnitController {

    private final BusinessUnitRepository businessUnitRepository;
    private final ConversionService conversionService;

    public BusinessUnitController(BusinessUnitRepository businessUnitRepository, ConversionService conversionService) {
        this.businessUnitRepository = businessUnitRepository;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<List<BusinessUnitDto>> getBusinessUnits() {
        List<BusinessUnitDto> bus = businessUnitRepository.findAll().stream()
                .map(businessUnit ->
                        conversionService.convert(businessUnit, BusinessUnitDto.class))
                .toList();
        return ResponseEntity.ok(bus);
    }
}
