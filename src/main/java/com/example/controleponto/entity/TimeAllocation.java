package com.example.controleponto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeAllocation {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long workdayId;
    private String description;
    private Long secondsAllocated;
}
