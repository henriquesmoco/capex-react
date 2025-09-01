package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.request.dto.RequestCreateDto;
import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public RequestDto getRequestById(@PathVariable Long id) {
        return conversionService.convert(requestRepository.findById(id).orElseThrow(), RequestDto.class);
    }

    @PostMapping
    public RequestDto createRequest(@RequestBody @Validated RequestCreateDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request");
        }
        Request request = conversionService.convert(requestDto, Request.class);
        assert request != null;
        requestRepository.save(request);

        return conversionService.convert(request, RequestDto.class);
    }
}
