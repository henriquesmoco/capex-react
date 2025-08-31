package com.hsmoco.capex.capexbackend.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;
import org.mapstruct.extensions.spring.converter.ConversionServiceAdapter;

@MapperConfig(componentModel = "spring", uses = ConversionServiceAdapter.class)
@SpringMapperConfig(generateConverterScan = true,
        conversionServiceAdapterPackage = "org.mapstruct.extensions.spring.converter",
        conversionServiceBeanName = "mvcConversionService")
public class MapperSpringConfig {
}
