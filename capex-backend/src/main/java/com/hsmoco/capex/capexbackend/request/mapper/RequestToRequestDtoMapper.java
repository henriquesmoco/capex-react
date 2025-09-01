package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface RequestToRequestDtoMapper extends Converter<Request, RequestDto> {
}
