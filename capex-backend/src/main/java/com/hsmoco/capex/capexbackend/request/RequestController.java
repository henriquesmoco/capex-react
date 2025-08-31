package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/requests")
public class RequestController {

    private final ConversionService conversionService;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestController(ConversionService conversionService, RequestRepository requestRepository) {
        this.conversionService = conversionService;
        this.requestRepository = requestRepository;
    }

    @GetMapping
    public List<RequestDto> getRequests() {
        return requestRepository.findAll().stream()
                .map(request -> conversionService.convert(request, RequestDto.class))
                .toList();
    }
}
