package com.hsmoco.capex.capexbackend.query;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
public class FilterConversion {

    /**
     * Convert the filter string list to FilterParam list.<br>
     * @param filters list of filter strings: <code>fieldName(operation)value</code>
     * @return a list of <code>FilterParam</code> without nulls and unknown operations
     */
    public List<FilterParam> convert(List<String> filters) {
        if(filters == null) return List.of();
        return filters.stream().map(this::convert)
                .filter(Objects::nonNull)
                .filter(param -> param.operation() != FilterOperation.UNKNOWN)
                .toList();
    }

    /**
     * Convert filter string to FilterParam list.
     * @param filter fieldName(operation)value
     * @return FilterParam with decoded value or null if the filter is invalid
     */
    public FilterParam convert(String filter) {
        if(StringUtils.isBlank(filter)) return null;

        int opStart = filter.indexOf('(');
        int opEnd = filter.indexOf(')', opStart);
        if(opStart < 0 || opEnd < 0 || opStart == opEnd) {
            return null;
        }
        String fieldName = filter.substring(0, opStart);
        String operation = filter.substring(opStart + 1, opEnd);
        String value = filter.substring(opEnd + 1);
        if(StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(operation)) {
            return new FilterParam(fieldName,
                    FilterOperation.fromString(operation),
                    URLDecoder.decode(value, StandardCharsets.UTF_8));
        }
        return null;
    }
}
