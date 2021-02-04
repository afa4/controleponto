package com.example.controleponto.entity;

import lombok.Data;

@Data
public class TimeAllocation {
    private Long id;
    private Long workdayId;
    private String description;
    private Integer secondsAllocated;
}
