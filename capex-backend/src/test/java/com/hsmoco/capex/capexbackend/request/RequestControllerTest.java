package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import com.hsmoco.capex.capexbackend.request.model.BusinessUnit;
import com.hsmoco.capex.capexbackend.request.model.Category;
import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RequestControllerTest {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private RequestController requestController;

    @Test
    void getRequests_WhenRequestHasParent_ConvertDataCorrectly() {
        BusinessUnit businessUnit = businessUnitRepository.findAll().getFirst();
        Category category = categoryRepository.findAll().getFirst();
        Request parent = new Request();
        parent.setType(RequestType.CAR);
        parent.setProjectName("parent_project");
        parent.setRequestNumber("CAR1");
        requestRepository.save(parent);

        Request request = new Request();
        request.setBusinessUnit(businessUnit);
        request.setCategory(category);
        request.setParent(parent);
        request.setType(RequestType.CAR);
        request.setRequestNumber("CAR2");
        request.setProjectName("child_project");
        requestRepository.save(request);

        List<RequestDto> requests = requestController.getRequests();
        RequestDto requestDto = requests.stream().filter(r -> r.id().equals(request.getId())).findFirst().orElseThrow();
        assertThat(requestDto.requestNumber()).isEqualTo(request.getRequestNumber());
        assertThat(requestDto.projectName()).isEqualTo(request.getProjectName());
        assertThat(requestDto.businessUnit().id()).isEqualTo(request.getBusinessUnit().getId());
        assertThat(requestDto.category().id()).isEqualTo(request.getCategory().getId());
        assertThat(requestDto.parent().requestNumber()).isEqualTo(request.getParent().getRequestNumber());
    }
}
