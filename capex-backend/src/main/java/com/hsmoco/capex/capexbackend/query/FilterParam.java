package com.hsmoco.capex.capexbackend.query;

import java.time.LocalDate;

public record FilterParam(String fieldName, FilterOperation operation, String value) {

 public LocalDate valueAsLocalDate() {
        return LocalDate.parse(value);
    }
}
