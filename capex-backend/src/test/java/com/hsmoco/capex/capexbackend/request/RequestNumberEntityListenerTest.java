package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.model.Request;
import com.hsmoco.capex.capexbackend.request.model.RequestNumberEntityListener;
import com.hsmoco.capex.capexbackend.request.model.RequestType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequestNumberEntityListenerTest {

    private final RequestRepository requestRepository = mock(RequestRepository.class);

    @Test
    void generateNumber_WithSavedEntity_DontGenerateRequestNumber() {
        RequestNumberEntityListener listener = new RequestNumberEntityListener(requestRepository);
        Request request = new Request();
        request.setId(1L);
        request.setType(RequestType.CAR);

        listener.generateRequestNumber(request);

        assertThat(request.getRequestNumber()).isNull();
        verify(requestRepository, never()).getNextRequestNumber();
        verify(requestRepository, never()).countByParent(any());
    }

    @Test
    void generateNumber_WithoutParent_GenerateCorrectRequestNumber() {
        when(requestRepository.getNextRequestNumber()).thenReturn(12L);
        RequestNumberEntityListener listener = new RequestNumberEntityListener(requestRepository);
        Request request = new Request();
        request.setType(RequestType.CAR);

        listener.generateRequestNumber(request);

        assertThat(request.getRequestNumber()).isNotNull();
        assertThat(request.getRequestNumber()).isEqualTo("CAR0012");
    }

    @Test
    void generateNumber_WithParent_GenerateCorrectRequestNumber() {
        when(requestRepository.countByParent(any())).thenReturn(1L);
        RequestNumberEntityListener listener = new RequestNumberEntityListener(requestRepository);
        Request parent = new Request();
        parent.setRequestNumber("CAR0001");
        Request request = new Request();
        request.setType(RequestType.CAR);
        request.setParent(parent);

        listener.generateRequestNumber(request);

        assertThat(request.getRequestNumber()).isNotNull();
        assertThat(request.getRequestNumber()).isEqualTo("CAR0001-S2");
        verify(requestRepository, never()).getNextRequestNumber();
    }
}
