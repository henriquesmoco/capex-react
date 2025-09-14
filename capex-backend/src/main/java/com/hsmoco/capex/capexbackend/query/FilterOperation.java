package com.hsmoco.capex.capexbackend.query;

import lombok.Getter;

@Getter
public enum FilterOperation {
    STARTS_WITH("startsWith"),
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    ENDS_WITH("endsWith"),
    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    DATE_IS("dateIs"),
    DATE_IS_NOT("dateIsNot"),
    DATE_BEFORE("dateBefore"),
    DATE_AFTER("dateAfter"),
    UNKNOWN("unknown");

    private final String operation;

    FilterOperation(String operation) {
        this.operation = operation;
    }

    public static FilterOperation fromString(String operation) {
        for (FilterOperation filterOperation : FilterOperation.values()) {
            if (filterOperation.getOperation().equalsIgnoreCase(operation)) {
                return filterOperation;
            }
        }
        return UNKNOWN;
    }
}
