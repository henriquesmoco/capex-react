package com.hsmoco.capex.capexbackend.request.mapper;

import com.hsmoco.capex.capexbackend.request.dto.CategoryDto;
import com.hsmoco.capex.capexbackend.request.model.Category;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends Converter<Category, CategoryDto> {
}
