package com.hsmoco.capex.capexbackend.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsmoco.capex.capexbackend.CapexBackendApplication;
import com.hsmoco.capex.capexbackend.request.dto.RequestCreateDto;
import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import com.hsmoco.capex.capexbackend.request.dto.RequestEditDto;
import com.hsmoco.capex.capexbackend.request.model.BusinessUnit;
import com.hsmoco.capex.capexbackend.request.model.Category;
import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CapexBackendApplication.class)
@AutoConfigureMockMvc
@Transactional
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @WithMockUser(username = "admin")
    @Test
    void createRequest_WithValidData_SaveAndReturnRequestWithID() throws Exception {
        Long businessUnitId = businessUnitRepository.findAll().getFirst().getId();
        Long categoryId = categoryRepository.findAll().getFirst().getId();
        Request parent = new Request();
        parent.setType(RequestType.CAR);
        parent.setProjectName("parent_project");
        parent.setRequestNumber("CAR1");
        Long parentId = requestRepository.save(parent).getId();

        RequestCreateDto requestDto = new RequestCreateDto(RequestType.CAR,"ProjName", LocalDate.now(),
                "Description", 1.0, 10.0, false, false,
                parentId, categoryId, businessUnitId);

        mockMvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.category.id").value(equalTo(categoryId), Long.class))
                .andExpect(jsonPath("$.businessUnit.id").value(equalTo(businessUnitId), Long.class))
                .andExpect(jsonPath("$.parent.id").value(equalTo(parentId), Long.class));
    }

    @WithMockUser(username = "admin")
    @Test
    void updateRequest_WithRequestNotfound_Return404() throws Exception {
        RequestEditDto requestDto = new RequestEditDto(0L, "ProjName", null,
                null,null,null,null,null,1L,1L);

        mockMvc.perform(put("/api/requests/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin")
    @Test
    void updateRequest_WithInvalidRequest_ReturnBadRequest() throws Exception {
        RequestEditDto requestDto = new RequestEditDto(null, null, null,
                null,null,null,null,null,null,null);

        mockMvc.perform(put("/api/requests/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
