package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.model.Request;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public class LongToRequestMapper implements Converter<Long, Request> {

    @Override
    public Request convert(Long source) {
        return new Request(source);
    }
}
