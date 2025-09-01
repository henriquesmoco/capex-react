package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.dto.RequestCreateDto;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring",
        uses = {LongToRequestMapper.class, LongToCategoryMapper.class, LongToBusinessUnitMapper.class})
public interface RequestCreateDtoToRequestMapper extends Converter<RequestCreateDto, Request> {

    @Mapping(target = "parent", source = "parentId")
    @Mapping(target = "category", source = "categoryId")
    @Mapping(target = "businessUnit", source = "businessUnitId")
    @Override
    Request convert(RequestCreateDto requestCreateDto);
}
