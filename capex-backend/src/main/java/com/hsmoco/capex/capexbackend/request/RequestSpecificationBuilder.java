package com.hsmoco.capex.capexbackend.request;

import com.hsmoco.capex.capexbackend.query.FilterParam;
import com.hsmoco.capex.capexbackend.request.model.Request;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RequestSpecificationBuilder {

    public static Specification<Request> build(List<FilterParam> filterParams) {
        Specification<Request> spec = null;
        for (FilterParam filterParam : filterParams) {
            if (spec == null) {
                spec = Specification.allOf(build(filterParam));
            } else {
                spec.and(build(filterParam));
            }
        }
        return spec;
    }

    public static Specification<Request> build(FilterParam filterParam) {
        return (root, query, criteriaBuilder) -> {
            return switch (filterParam.operation()) {
                case STARTS_WITH ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(filterParam.fieldName())), filterParam.value().toLowerCase() + "%");
                case CONTAINS ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(filterParam.fieldName())), "%" + filterParam.value().toLowerCase() + "%");
                case NOT_CONTAINS ->
                        criteriaBuilder.notLike(criteriaBuilder.lower(root.get(filterParam.fieldName())), "%" + filterParam.value().toLowerCase() + "%");
                case ENDS_WITH ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(filterParam.fieldName())), "%" + filterParam.value().toLowerCase());
                case EQUALS ->
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get(filterParam.fieldName())), filterParam.value().toLowerCase());
                case NOT_EQUALS ->
                        criteriaBuilder.notEqual(criteriaBuilder.lower(root.get(filterParam.fieldName())), filterParam.value().toLowerCase());
                case LESS_THAN ->
                        criteriaBuilder.lessThan(root.get(filterParam.fieldName()), filterParam.value());
                case LESS_THAN_OR_EQUAL_TO ->
                        criteriaBuilder.lessThanOrEqualTo(root.get(filterParam.fieldName()), filterParam.value());
                case GREATER_THAN ->
                        criteriaBuilder.greaterThan(root.get(filterParam.fieldName()), filterParam.value());
                case GREATER_THAN_OR_EQUAL_TO ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get(filterParam.fieldName()), filterParam.value());
                case DATE_IS ->
                        criteriaBuilder.equal(root.get(filterParam.fieldName()), filterParam.valueAsLocalDate());
                case DATE_IS_NOT ->
                        criteriaBuilder.notEqual(root.get(filterParam.fieldName()), filterParam.valueAsLocalDate());
                case DATE_BEFORE ->
                        criteriaBuilder.lessThan(root.get(filterParam.fieldName()), filterParam.valueAsLocalDate());
                case DATE_AFTER ->
                        criteriaBuilder.greaterThan(root.get(filterParam.fieldName()), filterParam.valueAsLocalDate());
                case UNKNOWN -> throw new IllegalArgumentException("Unknown filter operation: " + filterParam.operation());
            };
        };
    }
}
