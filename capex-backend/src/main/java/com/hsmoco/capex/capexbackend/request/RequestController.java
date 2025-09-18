package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.query.FilterConversion;
import com.hsmoco.capex.capexbackend.query.FilterParam;
import com.hsmoco.capex.capexbackend.request.dto.RequestCreateDto;
import com.hsmoco.capex.capexbackend.request.dto.RequestDto;
import com.hsmoco.capex.capexbackend.request.dto.RequestEditDto;
import com.hsmoco.capex.capexbackend.businessunit.BusinessUnit;
import com.hsmoco.capex.capexbackend.category.Category;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "api/requests")
public class RequestController {

    private final ConversionService conversionService;
    private final RequestRepository requestRepository;
    private final FilterConversion filterConversion;

    @Autowired
    public RequestController(ConversionService conversionService, RequestRepository requestRepository, FilterConversion filterConversion) {
        this.conversionService = conversionService;
        this.requestRepository = requestRepository;
        this.filterConversion = filterConversion;
    }

    @GetMapping
    public Page<RequestDto> getRequests(@PageableDefault Pageable pageable, @RequestParam(required = false) List<String> filter) {
        List<FilterParam> filterParams = filterConversion.convert(filter);
        if (filterParams.isEmpty()) {
            return requestRepository.findAll(pageable)
                    .map(request -> conversionService.convert(request, RequestDto.class));
        } else {
            Specification<Request> spec =  RequestSpecificationBuilder.build(filterParams);
            return requestRepository.findAll(spec, pageable)
                    .map(request -> conversionService.convert(request, RequestDto.class));
        }
    }

    @GetMapping("/{id}")
    public RequestDto getRequestById(@PathVariable Long id) {
        return conversionService.convert(requestRepository.findById(id).orElseThrow(), RequestDto.class);
    }

    @PutMapping("/{id}")
    public RequestDto updateRequest(@PathVariable Long id, @RequestBody @Validated RequestEditDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid request");
        }
        Request request = requestRepository.findById(id).orElseThrow();
        request.setProjectName(requestDto.projectName());
        request.setProjectDate(requestDto.projectDate());
        request.setDescription(requestDto.description());
        request.setCapexCost(BigDecimal.valueOf(requestDto.capexCost()));
        request.setOpexCost(BigDecimal.valueOf(requestDto.opexCost()));
        request.setEmergency(requestDto.emergency());
        request.setItProject(requestDto.itProject());
        request.setCategory(new Category(requestDto.categoryId()));
        request.setBusinessUnit(new BusinessUnit(requestDto.businessUnitId()));

        requestRepository.save(request);
        return conversionService.convert(request, RequestDto.class);
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
