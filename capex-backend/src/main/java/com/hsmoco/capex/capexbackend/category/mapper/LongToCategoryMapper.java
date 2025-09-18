package com.hsmoco.capex.capexbackend.category.mapper;

import com.hsmoco.capex.capexbackend.category.Category;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public class LongToCategoryMapper implements Converter<Long, Category> {

    @Override
    public Category convert(Long source) {
        return new Category(source);
    }
}
