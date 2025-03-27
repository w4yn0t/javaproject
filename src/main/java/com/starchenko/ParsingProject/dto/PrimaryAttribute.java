package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PrimaryAttribute {
    AGI("agi"),
    STR("str"),
    INT("int"),
    ALL("all");

    private final String value;
    PrimaryAttribute(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PrimaryAttribute forValue(String value) {
        for (PrimaryAttribute attr : PrimaryAttribute.values()) {
            if (attr.value.equalsIgnoreCase(value)) {
                return attr;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
