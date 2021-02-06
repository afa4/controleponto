package com.example.controleponto.entity.enumeration;

import lombok.Getter;

@Getter
public enum TimeRegisterType {
    STARTED_AT("started_at"),
    PAUSED_AT("started_at"),
    RETURNED_AT("started_at"),
    ENDED_AT("started_at");

    private final String value;

    TimeRegisterType(String value) {
        this.value = value;
    }
}
