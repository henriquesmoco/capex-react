package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.dto.BusinessUnitDto;
import com.hsmoco.capex.capexbackend.request.model.BusinessUnit;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface BusinessUnitMapper extends Converter<BusinessUnit, BusinessUnitDto> {
}
