package com.hsmoco.capex.capexbackend;

import com.hsmoco.capex.capexbackend.request.dto.RequestCreateDto;
import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConversionServiceConfigTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    void convert_RequestCreatedDtoToRequest_ConvertCorrectly() {
        Long parentId = 1L;
        Long categoryId = 2L;
        Long businessUnitId = 3L;
        RequestCreateDto requestDto = new RequestCreateDto(RequestType.CAR,"ProjName", LocalDate.now(),
                "Description", 1.0, 10.0, false, false,
                parentId, categoryId, businessUnitId);

        Request result = conversionService.convert(requestDto, Request.class);
        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo(RequestType.CAR);
        assertThat(result.getProjectName()).isEqualTo("ProjName");
        assertThat(result.getParent().getId()).isEqualTo(parentId);
        assertThat(result.getCategory().getId()).isEqualTo(categoryId);
        assertThat(result.getBusinessUnit().getId()).isEqualTo(businessUnitId);
    }
}
