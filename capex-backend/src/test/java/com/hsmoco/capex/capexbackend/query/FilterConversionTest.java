package com.hsmoco.capex.capexbackend.query;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FilterConversionTest {

    @ParameterizedTest
    @MethodSource("testSource_ConvertStringToParam")
    public void convertFilter_WithString_ReturnExpectedFilterParam(String filter, FilterParam expectedFilterParam) {
        FilterConversion filterConversion = new FilterConversion();
        FilterParam param = filterConversion.convert(filter);
        assertThat(param).isEqualTo(expectedFilterParam);
    }

    static Stream<Arguments> testSource_ConvertStringToParam() {
        return Stream.of(
                arguments("", null),
                arguments("[equals]john", null),
                arguments("name[]john", null),
                arguments("[]john", null),
                arguments("[]", null),
                arguments("name]equals[john", null),
                arguments("name[equals]john", new FilterParam("name",FilterOperation.EQUALS,"john")),
                arguments("name[equals]john%20doe", new FilterParam("name",FilterOperation.EQUALS,"john doe")),
                arguments("name[equals]", new FilterParam("name",FilterOperation.EQUALS,"")),
                arguments("age[lt]10", new FilterParam("age",FilterOperation.LESS_THAN,"10")),
                arguments("name[what]john", new FilterParam("name",FilterOperation.UNKNOWN,"john"))
        );
    }
}
