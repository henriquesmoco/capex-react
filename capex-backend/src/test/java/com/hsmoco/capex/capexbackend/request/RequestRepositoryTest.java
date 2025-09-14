package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.query.FilterOperation;
import com.hsmoco.capex.capexbackend.query.FilterParam;
import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    @Test
    void saveRequest_WithChildren_SavesTheRequestCorrectly() {
        Request request = createRequest(RequestType.CAR, "CAR1", "project1");
        Request child1 = createRequest(RequestType.CAR, "CAR1-1", "child1");
        child1.setParent(request);
        request.getChildren().add(child1);
        Request child2 = createRequest(RequestType.CAR, "CAR1-2", "child2");
        child2.setParent(request);
        request.getChildren().add(child2);

        requestRepository.save(request);

        Request savedRequest = requestRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
        assertThat(savedRequest).isNotNull();
        assertThat(savedRequest.getChildren().size()).isEqualTo(2);
        assertThat(savedRequest.getChildren()).containsExactlyInAnyOrder(child1, child2);
    }

    @Test
    void saveRequest_WithoutParent_GeneratesRequestNumberOnSave() {
        Request request = createRequest(RequestType.LAR, null, "project1");

        requestRepository.save(request);

        Request savedRequest = requestRepository.findById(request.getId()).orElseThrow(RuntimeException::new);
        assertThat(savedRequest.getRequestNumber()).isNotNull();
        assertThat(savedRequest.getRequestNumber()).startsWith("LAR");
        assertThat(savedRequest.getRequestNumber()).doesNotEndWith("0000");
    }

    @Test
    void getRequest_WithSpecificationText_ReturnsCorrectRequests() {
        Request request = createRequest(RequestType.CAR, "CAR1", "ABC");
        Request request2 = createRequest(RequestType.CAR, "CAR2", "DEF");
        requestRepository.save(request);
        requestRepository.save(request2);

        Specification<Request> spec = RequestSpecificationBuilder.build(
                List.of(new FilterParam("projectName", FilterOperation.EQUALS, "def")));

        List<Request> result = requestRepository.findAll(spec);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactly(request2);
    }

    @Test
    void getRequest_WithSpecificationNumber_ReturnsCorrectRequests() {
        Request request = createRequest(RequestType.CAR, "CAR1", "ABC");
        request.setCapexCost(BigDecimal.valueOf(10L));
        Request request2 = createRequest(RequestType.CAR, "CAR2", "DEF");
        request2.setCapexCost(BigDecimal.valueOf(20L));

        requestRepository.save(request);
        requestRepository.save(request2);

        Specification<Request> spec = RequestSpecificationBuilder.build(
                List.of(new FilterParam("capexCost", FilterOperation.GREATER_THAN, "15")));

        List<Request> result = requestRepository.findAll(spec);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactly(request2);
    }

    @Test
    void getRequest_WithSpecificationDate_ReturnsCorrectRequests() {
        Request request = createRequest(RequestType.CAR, "CAR1", "ABC");
        request.setProjectDate(LocalDate.of(2020, 5, 1));
        Request request2 = createRequest(RequestType.CAR, "CAR2", "DEF");
        request2.setProjectDate(LocalDate.of(2020, 1, 1));

        requestRepository.save(request);
        requestRepository.save(request2);

        Specification<Request> spec = RequestSpecificationBuilder.build(
                List.of(new FilterParam("projectDate", FilterOperation.DATE_AFTER, "2020-04-01")));

        List<Request> result = requestRepository.findAll(spec);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).containsExactly(request);
    }

    private Request createRequest(RequestType type, String reqNum, String projName) {
        Request request = new Request();
        request.setType(type);
        request.setRequestNumber(reqNum);
        request.setProjectName(projName);
        return request;
    }
}
