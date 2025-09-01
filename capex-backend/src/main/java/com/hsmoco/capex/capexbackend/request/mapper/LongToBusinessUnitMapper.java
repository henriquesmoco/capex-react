package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.model.BusinessUnit;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public class LongToBusinessUnitMapper implements Converter<Long, BusinessUnit> {

    @Override
    public BusinessUnit convert(Long source) {
        return new BusinessUnit(source);
    }
}
