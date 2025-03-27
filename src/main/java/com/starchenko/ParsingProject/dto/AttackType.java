package com.starchenko.ParsingProject.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AttackType {
    MELEE("Melee"),
    RANGED("Ranged");

    private final String value;
    AttackType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AttackType forValue(String value) {
        for (AttackType attackType : AttackType.values()) {
            if (attackType.value.equalsIgnoreCase(value)) {
                return attackType;
            }
        }
        throw new IllegalArgumentException("Unknown attack type: " + value);
    }
}
