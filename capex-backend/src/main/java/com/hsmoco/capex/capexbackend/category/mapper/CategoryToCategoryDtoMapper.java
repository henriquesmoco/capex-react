package com.hsmoco.capex.capexbackend.category.mapper;

import com.hsmoco.capex.capexbackend.category.dto.CategoryDto;
import com.hsmoco.capex.capexbackend.category.Category;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CategoryToCategoryDtoMapper extends Converter<Category, CategoryDto> {
}
