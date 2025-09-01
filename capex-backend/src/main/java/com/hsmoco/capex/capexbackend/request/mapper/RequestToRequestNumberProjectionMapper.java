package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.dto.RequestNumberProjectionDto;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface RequestToRequestNumberProjectionMapper extends Converter<Request, RequestNumberProjectionDto> {
}
