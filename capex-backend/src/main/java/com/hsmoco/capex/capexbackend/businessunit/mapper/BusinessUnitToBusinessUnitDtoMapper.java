package com.hsmoco.capex.capexbackend.businessunit.mapper;

import com.hsmoco.capex.capexbackend.businessunit.BusinessUnit;
import com.hsmoco.capex.capexbackend.businessunit.dto.BusinessUnitDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface BusinessUnitToBusinessUnitDtoMapper extends Converter<BusinessUnit, BusinessUnitDto> {
}
