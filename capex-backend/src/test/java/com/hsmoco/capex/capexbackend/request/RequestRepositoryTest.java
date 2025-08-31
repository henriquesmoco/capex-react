package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    private Request createRequest(RequestType type, String reqNum, String projName) {
        Request request = new Request();
        request.setType(type);
        request.setRequestNumber(reqNum);
        request.setProjectName(projName);
        return request;
    }
}
