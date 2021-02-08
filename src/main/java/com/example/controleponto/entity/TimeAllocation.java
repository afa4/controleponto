package com.example.controleponto.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeAllocation {
    private Long id;
    private Long workdayId;
    private String description;
    private Long secondsAllocated;
}
