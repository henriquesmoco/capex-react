package com.hsmoco.capex.capexbackend.businessunit.mapper;

import com.hsmoco.capex.capexbackend.businessunit.BusinessUnit;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public class LongToBusinessUnitMapper implements Converter<Long, BusinessUnit> {

    @Override
    public BusinessUnit convert(Long source) {
        return new BusinessUnit(source);
    }
}
